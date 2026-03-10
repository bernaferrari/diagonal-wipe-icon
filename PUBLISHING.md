# Publishing

This repo publishes only the `:lib` module to Maven Central.

## One-time setup

1. Verify the `io.github.bernaferrari` namespace in Sonatype Central Portal.
2. Generate a Central Portal user token.
3. Create a GPG key and distribute the public key.

## Local release

Put the following in `~/.gradle/gradle.properties` or export them as environment variables:

```properties
mavenCentralUsername=...
mavenCentralPassword=...
signingInMemoryKey=...
signingInMemoryKeyId=...
signingInMemoryKeyPassword=...
```

Then publish a release:

```bash
./gradlew :lib:publishToMavenCentral -PVERSION_NAME=0.1.0
```

Because automatic release is enabled, the build waits for Central validation/publication before succeeding.

## Local smoke test

```bash
./gradlew :lib:publishToMavenLocal
```

## GitHub Actions secrets

Configure these repository secrets:

- `MAVEN_CENTRAL_USERNAME`
- `MAVEN_CENTRAL_PASSWORD`
- `SIGNING_KEY`
- `SIGNING_KEY_ID`
- `SIGNING_PASSWORD`

## GitHub Actions release

Push a tag like `v0.1.0` to publish that version:

```bash
git tag v0.1.0
git push origin v0.1.0
```
