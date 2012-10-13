The java classes in this project are directly generated from [libbash](http://www.gentoo.org/proj/en/libbash/index.xml).

This is what you need to install on a gentoo server to get libbash to compile and generate 
the java classes.

    emerge --sync
    emerge git paludis vim dev-libs/boost oracle-jdk-bin gtest antlr antlr-c

You will have to play with use flags to get everything there to install.

Then just checkout the source

    git clone git://git.overlays.gentoo.org/proj/libbash.git

Configure and build the package

    cd libbash; ./autogen.sh; make

