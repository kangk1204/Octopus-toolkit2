Octopus-toolkit
===============

Octopus-toolkit

<2017-04-06 17:02:12 by Prof. Keunsoo Kang, Taemook Kim in the Kangklab>

Octopus-toolkit is a stand-alone application for retrieving and processing large sets of next-generation sequencing (NGS) data with a single step. Octopus-toolkit is an automated set-up-and-analysis pipeline utilizing the Aspera, SRA Toolkit, bwtool, Samtools FastQC, Trimmomatic, HISAT2, STAR, and HOMER applications. All the applications will be installed on the user’s computer when the program starts. Upon the installation, it can automatically retrieve original files (.SRA) of various data sets, including ChIP-seq, ATAC-seq, DNase-seq, MeDIP-seq, MNase-seq, and RNA-seq, from the gene expression omnibus data repository. The downloaded files can then be sequentially processed to generate BAM and BigWig files, which are used for advanced analyses and visualization. Currently, it can process NGS data from popular model genomes such as, human (Homo sapiens), mouse (Mus musculus), dog (Canis lupus familiaris), Fruit fly (Drosophila melanogaster), Zebrafish (Danio rerio), Arabidopsis (Arabidopsis thaliana), budding yeast (Saccharomyces cerevisiae), and Worm (c.elegans) genomes. With the processed files from Octopus-toolkit, the meta-analysis of various data sets, motif searches for DNA-binding proteins, and the identification of differentially expressed genes and/or protein-binding sites can be easily conducted with few commands by users. Octopus-toolkit can allow biologist and other researchers to run NGS analysis without understanding of computation behind the tools.

Hardware/Software Requirement
-----------------------------

Minimum Memory (RAM):

* ``32Gb`` memory for RNA-Seq.
* ``8Gb`` memory for Others (ChIP,ATAC,MNase,DNase,MeDIP)

Operating System:

* 32-64bit Linux, 64bit Window.

Operating System Version (tested):

* Linux : Ubuntu (``14.04``),(``16.04``, highly recommend)
* Fedora (``22``),(``25``)
* Mint (``18``)
* CentOS (``7``)
* MacOS (``Sierra.10.12.6``)

Program development
-------------------

* Eclipse : Neon.1a Service Release(4.6.1)
* Language : Java Programming language (JDK1.8)
* Graphic User Interface(GUI) : Swing & Windowbuilder


Download
--------

``Latest Version (2.0.8)`` : (:download:`Octopus-toolkit<_templates/Octopus-toolkit.zip>`),  release 12/22/2017

  - Version(``2.0.8``) is a minor release with the following changes.
  - Modified a list of the private table.
  - Changed source code (Private Table) of Octopus-toolkit to make maintenance easier.

``Latest Mac Version (2.0.8)``: (:download:`Octopus-toolkit_mac<_templates/Octopus-toolkit_mac.zip>`),  release 12/22/2017

  - Version(``2.0.8``) is a minor release with the following changes.
  - Modified a list of the private table.
  - Changed source code (Private Table) of Octopus-toolkit to make maintenance easier.

--------

``Version (2.0.7)``,  release 12/11/2017

  - Version(``2.0.7``) is a minor release with the following changes.
  - modified a parsing code because the format of the NCBI's GEO Accession display is changed.

``Mac Version (2.0.7)``,  release 12/11/2017

  - Version(``2.0.7``) is a minor release with the following changes.
  - modified a parsing code because the format of the NCBI's GEO Accession display is changed.

---------------------------------------------------------------------------------------

``Version (2.0.6)``,  release 11/28/2017

  - Version(``2.0.6``) is a minor release with the following changes.
  - Changed a method which to obtain modified url of the raw data in NCBI.
  - Display Microarray in unsupported list (Err004-2) (NULL -> Microarray)

``Mac Version (2.0.6)``,  release 11/28/2017

  - Version(``2.0.5``) is a minor release with the following changes.
  - Changed a method which to obtain modified url of the raw data in NCBI.
  - Display Microarray in unsupported list (Err004-2) (NULL -> Microarray)

---------------------------------------------------------------------------------------

``Version (2.0.5)``,  release 11/08/2017

  - Version(``2.0.5``) is a minor release with the following changes.
  - Regular inspection of source code.

``Mac Version (2.0.5)``,  release 11/08/2017

  - Version(``2.0.5``) is a minor release with the following changes.
  - Regular inspection of source code.

---------------------------------------------------------------------------------------

``Version (2.0.4)``,  release 11/07/2017

  - Version(``2.0.4``) is a minor release with the following changes.
  - Applied the modified url of raw data in GEO Dataset. (Issue : changed FTP path of SRA experiment data in NCBI) 

``Mac Version (2.0.4)``,  release 11/07/2017

  - Version(``2.0.4``) is a minor release with the following changes.
  - Applied the modified url of raw data in GEO Dataset. (Issue : changed FTP path of SRA experiment data in NCBI)

---------------------------------------------------------------------------------------

``Version (2.0.3)``,  release 10/23/2017

  - Version(``2.0.3``) is a minor release with the following changes.
  - Updated ``CentOS`` version
  - Optimized Mapping process.
  - Changed source code of Octopus-toolkit to make maintenance easier.

``Mac Version (2.0.3)``,  release 10/23/2017

  - Version(``2.0.3``) is a minor release with the following changes.
  - Optimized Mapping process.
  - Changed source code of Octopus-toolkit to make maintenance easier.

---------------------------------------------------------------------------------------

``Version (2.0.2)`` ,  release 09/09/2017

  - Version(``2.0.2``) is a minor release with the following changes.
  - Modified a Full parameter Option.
  - Changed source code of Octopus-toolkit to make maintenance easier.

``Mac Version (2.0.2)``,  release 09/09/2017

---------------------------------------------------------------------------------------

``Version (2.0.1)``,  release 08/23/2017

  - Version(``2.0.1``) is a minor release with the following changes.
  - Change 3rd party tools to be installed without password.
  - When analysis is completed/failed, notify the path of results in a terminal window.
  - Changed source code of Octopus-toolkit to make maintenance easier.
  - Changed User Interface(UI) of the installation progressbar.
  - Added Ubuntu 14.04, Refer to :ref:`"How to install libraries" <requirement>`, :ref:`"How to install R" <graph>`
  - Added the ``Tutorial`` about how to use a custom adapter sequence generated by ownself and how to discover de novo and known motif using the output file of Octopus-toolkit. 


``Beta Version (2.0.0)``,  release 07/29/2017

---------------------------------------------------------------------------------------

=================
Table of contents
=================

.. toctree::
   :maxdepth: 2

   quick_start
   installation
   run
   folder
   user_interface
   guide
   tutorial
   error
   license
   help
   window
   3rd_party_version

..

