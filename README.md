# Setup
make sure build.gradle meets the git directory

or add attribute in asciidoc file:

`:git: path/to/git/excec`

build with:

`gradle build asciidoctor`


# usage in asciidoc:

Attribute encoding is optional; default UTF-8

`include::git@path/to/source.extension[repodir="path/where/.git/will/be",branch="master",encoding="UTF-8"]`
