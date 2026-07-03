# NeoLib

NeoLib is the shared plugin library for my Paper plugins.

It ships common dependencies and small helpers in one plugin, so the other plugins can stay lighter.

## Server install

1. Download the latest `NeoLib-*.jar` from Releases.
2. Put it in `server/plugins/`.
3. Restart the server.

Plugins that use NeoLib should keep this in `plugin.yml`:

```yaml
depend: [NeoLib]
```

## Build locally

```bash
./gradlew jar
```

The jar will be in:

```text
build/libs/
```

## Make a release

Use a git tag:

```bash
git tag v1.0.0
git push origin v1.0.0
```

GitHub Actions will build the jar and attach it to a GitHub Release.

You can also run the `Release` workflow manually from the Actions tab and type the version, like `v1.0.1`.

## Version note

This branch targets:

- Java 25
- Paper API 26.2
- InvUI 2.2.0

Keep NeoLib in `server/plugins/` before loading plugins that depend on it.

## License

NeoLib is licensed under the [GNU General Public License v3.0](LICENSE).

This is a **strong copyleft** license: anyone who uses, modifies, or distributes
NeoLib (or works based on it) must release their source code under the same
GPL-3.0 terms. In short, no closed-source derivatives allowed.
