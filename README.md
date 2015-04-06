# OpenShift WildFly JPA & JAX-RS Sample

This sample appears to deploy correctly to a small gear with MySQL and can actually load.

## Notes re: Deployment

The `.openshift/config/standalone.xml` file has been heavily modified, and several features were taken out to optimize the app to run on a small gear.

The app may still need to be restarted (in addition to redeployed) to clear enough memory for it to run.
