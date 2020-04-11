Octopus-toolkit
===============

Octopus-toolkit

.. note::
    ``2018-09-05`` : Octopus-toolkit supports Ubuntu18.04 version. Please feel free to contact us if you have any problems in use.

* Please cite the following paper : 

  - Kim T, Seo HD, Hennighausen L, Lee D, Kang K. Octopus-toolkit: a workflow to automate mining of public epigenomic and transcriptomic next-generation sequencing data. Nucleic Acids Res. 2018 Feb 6. doi: 10.1093/nar/gky083. PubMed PMID: `29420797 <https://www.ncbi.nlm.nih.gov/pubmed/29420797>`_

<2017-04-06 17:02:12 by Prof. Keunsoo Kang, Taemook Kim in the Kangklab>

Octopus-toolkit is a stand-alone application for retrieving and processing large sets of next-generation sequencing (NGS) data with a single step. Octopus-toolkit is an automated set-up-and-analysis pipeline utilizing the Aspera, SRA Toolkit, bwtool, Samtools FastQC, Trimmomatic, HISAT2, STAR, and HOMER applications. All the applications will be installed on the user’s computer when the program starts. Upon the installation, it can automatically retrieve original files (.SRA) of various data sets, including ChIP-seq, ATAC-seq, DNase-seq, MeDIP-seq, MNase-seq, and RNA-seq, from the gene expression omnibus data repository. The downloaded files can then be sequentially processed to generate BAM and BigWig files, which are used for advanced analyses and visualization. Currently, it can process NGS data from popular model genomes such as, human (Homo sapiens), mouse (Mus musculus), dog (Canis lupus familiaris), Fruit fly (Drosophila melanogaster), Zebrafish (Danio rerio), Arabidopsis (Arabidopsis thaliana), budding yeast (Saccharomyces cerevisiae), and Worm (c.elegans) genomes. With the processed files from Octopus-toolkit, the meta-analysis of various data sets, motif searches for DNA-binding proteins, and the identification of differentially expressed genes and/or protein-binding sites can be easily conducted with few commands by users. Octopus-toolkit can allow biologist and other researchers to run NGS analysis without understanding of computation behind the tools.

Download
--------

``Latest Version (2.2.0)`` : (:download:`Octopus-toolkit<_templates/Octopus-toolkit.zip>`),  release 04/11/2020

  - Version(``2.2.0``) is a major release with the following changes.
  - Upgraded versions of some tools
  - Changed the tool used to download raw data from NCBI
  - Fixed the issue of Err006-1 what raw data was not downloaded.
  
``Latest Mac Version (2.2.0)``: (:download:`Octopus-toolkit_mac<_templates/Octopus-toolkit_mac.zip>`),  release 04/11/2020

  - Version(``2.2.0``) is a major release with the following changes.
  - Upgraded versions of some tools
  - Changed the tool used to download raw data from NCBI
  - Fixed the issue of Err006-1 what raw data was not downloaded.

Hardware/Software Requirement
-----------------------------

Minimum Memory (RAM):

* ``32Gb`` memory for RNA-Seq.
* ``8Gb`` memory for Others (ChIP,ATAC,MNase,DNase,MeDIP)

Operating System:

* 32-64bit ``Linux``, 64bit ``MacOS``, 64bit ``Window(Alpha Version)``.

Operating System Version (tested):

* Linux : Ubuntu (``14.04``),(``16.04``, highly recommend), (``18.04``)
* Fedora (``22``),(``25``)
* Mint (``18``)
* CentOS (``7``)
* MacOS (``Sierra.10.12.6``)

Program development
-------------------

* Eclipse : Neon.1a Service Release(4.6.1)
* Language : Java Programming language (JDK1.8)
* Graphic User Interface(GUI) : Swing & Windowbuilder

Previous version
-------------------

``Latest Version (2.1.3)``, release 02/20/2019

  - Version(``2.1.3``) is a minor release with the following changes.
  - Optimized the analysis package for each operating system.
  - Adjusted threshold of the read min length in Trimming process.
  
``Latest Mac Version (2.1.3)``, release 02/20/2019

  - Version(``2.1.3``) is a minor release with the following changes.
  - Optimized the analysis package for each operating system.
  - Adjusted threshold of the read min length in Trimming process.

---------------------------------------------------------------------------------------

``Version (2.1.2)``,  release 09/05/2018

  - Version(``2.1.2``) is a minor release with the following changes.
  - Added new operating system to support Ubuntu ``18.04`` version.
  - Upgraded versions of R packages.
  - Optimized the analysis package for each operating system.
  
``Mac Version (2.1.2)``,  release 09/05/2018

  - Version(``2.1.2``) is a minor release with the following changes.
  - Upgraded versions of R packages.
  - Optimized the analysis package for each operating system.

---------------------------------------------------------------------------------------

``Version (2.1.1)``,  release 06/21/2018

  - Version(``2.1.1``) is a minor release with the following changes.
  - Changed source code (Download) of Octopus-toolkit to install Homer tool.
  
``Mac Version (2.1.1)``,  release 06/21/2018

  - Version(``2.1.1``) is a minor release with the following changes.
  - Changed source code (Download) of Octopus-toolkit to install Homer tool.

---------------------------------------------------------------------------------------

``Version (2.1.0)``,  release 02/13/2018

  - Version(``2.1.0``) is a minor release with the following changes.
  - Periodic inspection of source code
  - Change the server storage and link

``Mac Version (2.1.0)``,  release 02/13/2018

  - Version(``2.1.0``) is a minor release with the following changes.
  - Periodic inspection of source code
  - Change the server storage and link

---------------------------------------------------------------------------------------

``Version (2.0.9)``,  release 01/03/2018

  - Version(``2.0.9``) is a minor release with the following changes.
  - Optimized Paired-end classfication Method in Private Data.
  - Changed source code (Private Table) of Octopus-toolkit to make maintenance easier.

``Mac Version (2.0.9)``,  release 01/03/2018

  - Version(``2.0.9``) is a minor release with the following changes.
  - Optimized Paired-end classfication Method in Private Data.
  - Changed source code (Private Table) of Octopus-toolkit to make maintenance easier.

---------------------------------------------------------------------------------------

``Version (2.0.8)``,  release 12/22/2017

  - Version(``2.0.8``) is a minor release with the following changes.
  - Modified a list of the private table.
  - Changed source code (Private Table) of Octopus-toolkit to make maintenance easier.

``Mac Version (2.0.8)``,  release 12/22/2017

  - Version(``2.0.8``) is a minor release with the following changes.
  - Modified a list of the private table.
  - Changed source code (Private Table) of Octopus-toolkit to make maintenance easier.

---------------------------------------------------------------------------------------

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
  - Periodic inspection of source code.

``Mac Version (2.0.5)``,  release 11/08/2017

  - Version(``2.0.5``) is a minor release with the following changes.
  - Periodic inspection of source code.

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

