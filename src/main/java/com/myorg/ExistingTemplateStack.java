package com.myorg;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import software.amazon.awscdk.cloudformation.include.CfnInclude;
import software.amazon.awscdk.core.CfnParameter;
import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;

public class ExistingTemplateStack extends Stack {

	public ExistingTemplateStack(final Construct scope, final String id) {

		this(scope, id, StackProps.builder().env(Environment.builder().region("ap-south-1").build()).build());
	}

	public ExistingTemplateStack(final Construct scope, final String id, final StackProps props) {
		super(scope, id, props);

		// read an existing CF template
		final String rawtemplatePath = "/home/chetan/personal_projects/sceptre-example/templates/vpc.yaml";


		/*although its possible to pass params at compile time, 
		 * recommended way is to pass them at deploy time via cli parameters. see README*/
		/*below we are replacing the parameters in the raw CF template */
		Map<String, Object> cfParams = new HashMap<>();
		cfParams.put("CidrBlock", "10.0.0.0/16");
		cfParams.put("Environment", "dev") ;

		CfnInclude cfnInclude = CfnInclude.Builder.create(this, id)
				.templateFile(rawtemplatePath)
				.parameters(cfParams)
				.build();

	}

}
