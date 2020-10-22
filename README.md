# CDK examples with Java

This is a sample project for Java development with CDK.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

It is a [Maven](https://maven.apache.org/) based project, so you can open this project with any Maven compatible Java IDE to build and run tests.

## Useful commands

 * `mvn package`     compile and run tests
 * `cdk ls`          list all stacks in the app
 * `cdk synth`       emits the synthesized CloudFormation template
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk docs`        open CDK documentation
 * `cdk destroy`      to destroy the stacks created
 * `cdk deploy CdkBucketStack CdkExampleStack` to deploy multiple stacks (each stack to be specified)
 * `cdk destroy CdkBucketStack CdkExampleStack` to destroy multiple stacks


## Passing parameters at deployment time
Its recommended to pass parameters to CDK stacks at runtime.
Example:
`cdk deploy ExistingTemplateStack --parameters CidrBlock=192.168.101.0/24 --parameters Environment=dev`

Note that currently CDK has a issue with CidrBlock passed from CDK.
If you run the CloudFormation manually and specify same CidrBlock, it works.
But not via CDK..

## Passing parameters programmatically in code
Its possible to pass parameters via code rather than at build time.
However its not recommended.
Pls check `ExistingTemplateStack` for an example.

## CfnInclude - Two classes currently
CfnInclude can be used to directly re-use raw CF templates written in YAML or JSON.
This is offered as path to migrate from raw CF templates to CDK gradually.
There are currently two classes with some similar functionality for cfn include.
One is: `software.amazon.awscdk.core.CfnInclude`
Other is: `software.amazon.awscdk.cloudformation.include.CfnInclude`
Second one is experimental , but has some more features.
Details here: https://docs.aws.amazon.com/cdk/latest/guide/use_cfn_template.html
This repo uses the experimental one as it has more features , is in developer preview and will become the defacto implementation.

## CDK API not stable
As mentioned , just in POC couple of issues observed:
1. Passing valid value to CidrBlock in CDK gives error even though it works via Raw templates.
There is an issue open for this.. this issue happens only if we are passing params via CLI params (at deploy time). But does not happen if we pass this from code
2. Cfn Include dev preview API pulls in 200 MB of other dependencies. Experimental Cfn Include was merged in Sept 2020 (hardly a month ago!)