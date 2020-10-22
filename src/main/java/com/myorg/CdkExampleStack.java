package com.myorg;

import java.util.Arrays;
import java.util.List;

import software.amazon.awscdk.core.Construct;
import software.amazon.awscdk.core.Environment;
import software.amazon.awscdk.core.Stack;
import software.amazon.awscdk.core.StackProps;
import software.amazon.awscdk.services.autoscaling.AutoScalingGroup;
import software.amazon.awscdk.services.ec2.AmazonLinuxImage;
import software.amazon.awscdk.services.ec2.InstanceClass;
import software.amazon.awscdk.services.ec2.InstanceSize;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.elasticloadbalancing.HealthCheck;
import software.amazon.awscdk.services.elasticloadbalancing.ListenerPort;
import software.amazon.awscdk.services.elasticloadbalancing.LoadBalancer;
import software.amazon.awscdk.services.elasticloadbalancing.LoadBalancerListener;

public class CdkExampleStack extends Stack {
	public CdkExampleStack(final Construct scope, final String id) {
		this(scope, id, StackProps.builder().env(Environment.builder().region("ap-south-1").build()).build());
	}

	public CdkExampleStack(final Construct scope, final String id, final StackProps props) {
		super(scope, id, props);

		List<? extends SubnetConfiguration> subnetConfiguration = Arrays.asList(
				SubnetConfiguration.builder().cidrMask(24).name("Subnet01").subnetType(SubnetType.PRIVATE).build(),
				SubnetConfiguration.builder().cidrMask(24).name("Subnet02").subnetType(SubnetType.PRIVATE).build(),
				SubnetConfiguration.builder().cidrMask(24).name("Subnet03").subnetType(SubnetType.PUBLIC).build());

		Vpc vpc = Vpc.Builder.create(this, "newdevvpc").cidr("10.0.0.0/16").subnetConfiguration(subnetConfiguration)
				.build();

		AutoScalingGroup asg = AutoScalingGroup.Builder.create(this, "ASG").vpc(vpc)
				.instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO))
				.machineImage(new AmazonLinuxImage()).maxCapacity(1).build();

		HealthCheck.Builder healthCheckBuilder = new HealthCheck.Builder();
		HealthCheck healthCheck = healthCheckBuilder.port(80).build();
		LoadBalancer loadBalancer = LoadBalancer.Builder.create(this,"LB")
                .vpc(vpc)
                .internetFacing(Boolean.TRUE)
                .healthCheck(healthCheck)
                .build();
		
		loadBalancer.addTarget(asg);
		ListenerPort listenerPort = loadBalancer.addListener(LoadBalancerListener.builder().externalPort(80).build());
		listenerPort.getConnections().allowDefaultPortFromAnyIpv4("Open to the world");
		

	}
}
