==============
1.Installation
==============

1-1.Download
------------

.. note::
    ``Requirements`` must be installed on a computer before running the Octopus-toolkit.

Download Link : `Octopus-toolkit <https://github.com/kangk1204/Octopus/archive/master.zip>`_

.. _requirement:

1-2.Requirement
---------------

To run Octopus-toolkit, you must have a version of ``Java 8`` (JDK, Java Development ToolKit) or higher installed on a computer.

* Ubuntu, Mint ::

    sudo apt-get install openjdk-8-jdk

* Fedora ::

    sudo yum install java-1.8.0-openjdk

Octopus-toolkit requires several libraries for analysis.
Each operating system such as ubuntu,mint and fedora have different installation methods.
Please follow the installation methods below.

1-3.Ubuntu,Mint
---------------

To run tools in Octopus-toolkit, you must install libraries: ``zlib1g``, ``libpng12``, ``libncurses5``, ``g++``

* ``zlib1g-dev`` ::
    
    sudo apt-get install zlib1g-dev

* ``libpng12-dev`` ::
    
    sudo apt-get install libpng12-dev

* ``libncurses5-dev`` ::

    sudo apt-get install libncurses5-dev

* ``g++`` ::

    sudo apt-get install build-essential

OR ::

    sudo apt-get install zlib1g-dev libpng12-dev libncurses5-dev build-essential

1-4.Fedora
----------

To run tools in Octopus-toolkit, you must install libraries: ``zlib``, ``libpng`` , ``libpng12``, ``ncurses``, ``gcc-c++``

* ``zlib-devel`` ::

    sudo yum install zlib-devel.x86_64

* ``libpng-devel`` ::

    sudo yum install libpng-devel.x86_64

* ``libpng-devel12`` ::

    sudo yum install libpng12-devel.x86_64

* ``ncurses-devel`` ::

    sudo yum install ncurses-devel.x86_64

* ``gcc-c++`` ::

    sudo yum install gcc-c++

OR ::

    sudo yum install zlib-devel.x86_64 libpng-devel.x86_64 libpng12-devel.x86_64 ncurses-devel.x86_64 gcc-c++

1-5.Graph
---------

To draw heatmap and Line plot, you must have a version of ``R (3.1)`` or higher installed on a computer.

* Ubuntu, Mint ::

    sudo apt-get install r-base

* Fedora ::

    sudo yum install R

