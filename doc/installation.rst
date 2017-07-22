==============
1.Installation
==============

1-1.Download
------------

.. note::
    Applications in the ``Requirement`` section must be installed on your computer before running the Octopus-toolkit.

Download Link : (:download:`Octopus-toolkit<_templates/Octopus-toolkit.zip>`)

.. _requirement:

1-2.Requirement
---------------

To run the Octopus-toolkit, ``Java 8`` (JDK, Java Development ToolKit) or higher must be installed on your computer.

* Ubuntu, Mint (``Up to Ubuntu 16.04 or Mint18`` )::

    sudo apt-get update
    sudo apt-get install openjdk-8-jdk

* Fedora (``Up to Fedora 25`` )::

    sudo yum update
    sudo yum install java-1.8.0-openjdk

Octopus-toolkit utilizes several libraries for analysis.
Each operating system such as ubuntu, mint and fedora have different ways to install the applications.
Please follow the installation guide below.

1-3.Ubuntu, Mint
----------------

To run the Octopus-toolkit, you must install the following libraries: ``zlib1g``, ``libpng12``, ``libncurses5``, ``g++``

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

To run the Octopus-toolkit, you must install the following libraries: ``zlib``, ``libpng`` , ``libpng12``, ``ncurses``, ``gcc-c++``

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

To draw heatmap and Line plot, ``R (3.1)`` or higher version of it must be installed on your computer.

* Ubuntu, Mint ::

    sudo apt-get install r-base

* Fedora ::

    sudo yum install R

