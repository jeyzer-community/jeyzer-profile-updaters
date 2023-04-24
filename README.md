# jeyzer-profile-updaters
The Jeyzer Profile Updaters project contains the Jeyzer profile update logic and the Jeyzer build plugins (Maven and Gradle) which apply it.

The Jeyzer Profile Updater plugins permit to update automatically the Jeyzer analysis patterns (add/update/delete) in the Jeyzer master and shared profiles with the patterns generated out of the Jeyzer annotation processing.

For more details see the [Jeyzer documentation](https://jeyzer.org/docs/shared-profile/profile-updaters/).


Build instructions
------------------

Jeyzer Profile-Updaters project can be built with Maven.

Under the jeyzer-profile-updaters/core, plugins/maven/jeyzer-maven-plugin and current directories, execute :

> mvn clean package

The jeyzer-all project is responsible for calling all the current project builds.

License
-------

Copyright 2020-2023 Jeyzer.

Licensed under the [Mozilla Public License, Version 2.0](https://www.mozilla.org/media/MPL/2.0/index.815ca599c9df.txt)
