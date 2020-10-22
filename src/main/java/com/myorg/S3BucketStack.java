package com.myorg;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.s3.Bucket;
import software.amazon.awscdk.services.s3.BucketEncryption;

public class S3BucketStack extends Stack {

	public S3BucketStack(final Construct scope, final String id) {

		this(scope, id, StackProps.builder().env(Environment.builder().region("ap-south-1").build()).build());
	}

	public S3BucketStack(final Construct scope, final String id, final StackProps props) {
		super(scope, id, props);
		BucketEncryption encryption = BucketEncryption.KMS_MANAGED;
		Bucket bucket = Bucket.Builder.create(this, id).encryption(encryption).build();

	}

}
