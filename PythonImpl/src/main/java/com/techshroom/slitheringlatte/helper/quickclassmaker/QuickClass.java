package com.techshroom.slitheringlatte.helper.quickclassmaker;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;
import com.techshroom.slitheringlatte.python.annotations.InterfaceType;

public interface QuickClass {

	@AutoValue
	static abstract class Mix implements QuickClass {

		public static final Builder builder() {
			return new AutoValue_QuickClass_Mix.Builder();
		}

		@AutoValue.Builder
		public interface Builder {
			Builder originalPythonName(String name);

			Builder classDefinition(String def);

			ImmutableList.Builder<String> superInterfacesBuilder();

			Mix build();
		}

		Mix() {
		}

		@Override
		public InterfaceType.Value getType() {
			return InterfaceType.Value.MIX;
		}

		public abstract ImmutableList<String> getSuperInterfaces();
	}

	@AutoValue
	static abstract class Attribute implements QuickClass {

		public static final Builder builder() {
			return new AutoValue_QuickClass_Attribute.Builder();
		}

		@AutoValue.Builder
		public interface Builder {
			Builder originalPythonName(String name);

			Builder classDefinition(String def);

			Builder name(String name);

			Builder valueType(String name);

			Attribute build();
		}

		Attribute() {
		}

		@Override
		public InterfaceType.Value getType() {
			return InterfaceType.Value.ATTRIBUTE;
		}

		public abstract String getName();

		public abstract String getValueType();
	}

	@AutoValue
	static abstract class Method implements QuickClass {

		public static final Builder builder() {
			return new AutoValue_QuickClass_Method.Builder();
		}

		@AutoValue.Builder
		public interface Builder {
			Builder originalPythonName(String name);

			Builder classDefinition(String def);

			Builder methodName(String name);

			ImmutableList.Builder<String> parametersBuilder();

			Builder returnType(String type);

			Method build();
		}

		Method() {
		}

		@Override
		public InterfaceType.Value getType() {
			return InterfaceType.Value.METHOD;
		}

		public abstract String getMethodName();

		public abstract ImmutableList<String> getParameters();

		public abstract String getReturnType();
	}

	public InterfaceType.Value getType();

	String getClassDefinition();

	String getOriginalPythonName();

}
