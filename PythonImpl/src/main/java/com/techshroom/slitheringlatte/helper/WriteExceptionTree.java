package com.techshroom.slitheringlatte.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import javax.lang.model.element.Modifier;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.CharStreams;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.techshroom.slitheringlatte.Options;
import com.techshroom.slitheringlatte.python.Tuple;

/**
 * Tool for writing the Python exception tree in Java.
 * 
 * @author Kenzie Togami
 */
public final class WriteExceptionTree {
    private static final OptionParser parser = new OptionParser();
    private static final OptionSpec<String> exceptionTree = parser
            .accepts("exception-tree", "Exception tree file.")
            .withRequiredArg().defaultsTo(Options.STREAM);

    private WriteExceptionTree() {
    }

    /**
     * Entry point.
     * 
     * @param args
     *            - arguments
     */
    public static void main(String[] args) {
        OptionSet options = parser.parse(args);
        String eTreeSource = exceptionTree.value(options);
        Supplier<InputStream> stream = () -> System.in;
        if (!eTreeSource.equals(Options.STREAM)) {
            stream = () -> {
                try {
                    return new FileInputStream(eTreeSource);
                } catch (FileNotFoundException e) {
                    System.err.println("No such file " + eTreeSource);
                    System.exit(2);
                    return null;
                }
            };
        }
        try (InputStream st = stream.get();
                Reader r = new InputStreamReader(st, StandardCharsets.US_ASCII)) {
            processTree(CharStreams.readLines(r));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private static void processTree(List<String> lines) {
        Iterator<String> linesAsIterator = lines.iterator();
        Iterable<String> forEachTarget = () -> linesAsIterator;
        Deque<Tuple> superclasses = Lists.newLinkedList();
        Map<String, String> nameToSuper = Maps.newHashMap();
        String lastName = linesAsIterator.next();
        int lastDepth = 0;
        for (String line : forEachTarget) {
            int depth = line.lastIndexOf('-');
            String exceptionName = line.substring(depth + 2);
            if (exceptionName.contains("(")) {
                int paren_index = exceptionName.indexOf('(');
                @SuppressWarnings("unused")
                String platform_name = exceptionName;
                exceptionName = exceptionName.substring(0, paren_index - 1);
            }
            if (exceptionName.contains("[")) {
                int left_bracket = exceptionName.indexOf('[');
                exceptionName = exceptionName.substring(0, left_bracket - 1);
            }
            if (lastDepth < depth) {
                superclasses.push(Tuple.create(lastDepth, lastName));
            } else if (lastDepth > depth) {
                while (((Integer) superclasses.peek().get(0)) >= depth) {
                    superclasses.pop();
                }
            }
            nameToSuper.put(exceptionName, (String) superclasses.peek().get(1));
            lastName = exceptionName;
            lastDepth = depth;
        }
        nameToSuper.forEach(WriteExceptionTree::writeClass);
    }

    /**
     * Load contents of target. Any exception causes an Error to be thrown.
     * 
     * @param target
     *            - file to load
     * @return contents of target
     */
    public static String loadFromFile(String target) {
        try (Reader r = new FileReader(target)) {
            return CharStreams.toString(r);
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    private static final String constructor1jd =
            loadFromFile("src/main/resources/error/init1.txt");
    private static final String constructor2jd =
            loadFromFile("src/main/resources/error/init2.txt");

    private static void writeClass(String subc, String superc) {
        if (subc.equals("BaseException")) {
            // don't write base
            return;
        }
        String package_ = "com.techshroom.slitheringlatte.python.error";
        // while there is no self placeholder
        ClassName subClassName = ClassName.get(package_, subc);
        TypeSpec.Builder subClass =
                TypeSpec.classBuilder(subClassName.simpleName())
                        .addModifiers(Modifier.PUBLIC)
                        .superclass(ClassName.get(package_, superc));
        subClass.addJavadoc(fetchJavaDoc(subc));
        subClass.addField(FieldSpec
                .builder(TypeName.LONG, "serialVersionUID", Modifier.PRIVATE,
                        Modifier.STATIC, Modifier.FINAL).initializer("1L")
                .build());
        MethodSpec varargsConstructor =
                MethodSpec.constructorBuilder()
                        .addJavadoc(constructor1jd, subc)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(Object[].class, "args").varargs()
                        .addCode(generateConstr("args")).build();
        MethodSpec messageConstructor =
                MethodSpec.constructorBuilder()
                        .addJavadoc(constructor2jd, subc)
                        .addModifiers(Modifier.PUBLIC)
                        .addParameter(String.class, "message")
                        .addCode(generateConstr("message")).build();
        MethodSpec withTraceback =
                MethodSpec
                        .methodBuilder("with_traceback")
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(subClassName)
                        .addParameter(StackTraceElement[].class, "tb")
                        .addCode(
                                CodeBlock
                                        .builder()
                                        .addStatement(
                                                "super.with_traceback(tb)")
                                        .addStatement("return this").build())
                        .build();
        subClass.addMethods(ImmutableList.of(varargsConstructor,
                messageConstructor, withTraceback));
        JavaFile file =
                JavaFile.builder(package_, subClass.build()).indent("    ")
                        .skipJavaLangImports(true).build();
        try {
            file.writeTo(Paths.get("src/main/java"));
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    // for speed we'll only pull the python page once!
    private static final String BASE_URL = "https://docs.python.org/3/library/";
    private static final String DOC_LOCATION = BASE_URL + "exceptions.html";
    private static Document doc;

    private static String fetchJavaDoc(String subc) {
        if (doc == null) {
            setDoc();
        }
        // find the proper exception tag, which sits in front of the actual data
        // then pull the next tag
        return performPatching(doc.getElementById(subc).nextElementSibling(),
                subc) + "\n";
    }

    private static String performPatching(Element element, String name) {
        // patches up the python docs to be usable here

        // links that otherwise blow up
        element.select("a.reference.internal[href^=#]").forEach(
                e -> {
                    e.replaceWith(new TextNode("{@link " + e.attr("title")
                            + "}", BASE_URL));
                });
        // de-relativeize
        element.getAllElements().stream()
                .filter(e -> !e.attr("href").equals("")).forEach(e -> {
                    e.attr("href", e.absUrl("href"));
                });
        // patch out <p> tag, it's weird because HTML doesn't like hanging text
        Element childOne = element.child(0).clone();
        element.child(0).remove();
        return childOne.html() + element.html();
    }

    private static void setDoc() {
        try {
            doc = Jsoup.connect(DOC_LOCATION).get();
        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    private static CodeBlock generateConstr(String param) {
        return CodeBlock.builder().addStatement("super($L)", param).build();
    }
}
