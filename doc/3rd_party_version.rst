===================
11. 3rd Party Tools
===================

11-1.Version 2.0.8
^^^^^^^^^^^^^^^^^^

Octopus-toolkit utilizes the following 3rd party tools during the process.

.. list-table::
   :widths: 10 10 10
   :header-rows: 1

   * - 3rd party tool
     - Version
     - Function
   * - ``Aspera``
     - v3.7.2.141527
     - Download SRA files from NCBI
   * - ``SRAToolkit``
     - v2.8.2
     - Convert SRA files to Fastq files
   * - ``FastQC``
     - v0.11.5
     - Quality check for raw data
   * - ``Trimmomatic``
     - v0.36
     - Trimming for adapter sequence and portions of low-quality reads
   * - ``Hisat2``
     - v2.1.0
     - Indexing and Mapping to reference genome
   * - ``STAR``
     - v2.5.1
     - Indexing and Mapping to reference genome for RNA-Seq
   * - ``Homer``
     - v4.9.1
     - Create bigWig for visualization and Detect enriched regions by mapped reads 
   * - ``Bwtool``, ``libbeato``
     - v1.0
     - Calculate normalized values from bigWig files
   * - ``R``
     - v3.1
     - Draw the heatmap and line plot
   * - ``IGV``
     - v2.3.69
     - Explore the genome with processed data (bigWig files)
   * - ``Samtools``
     - v1.5
     - Sorting and Indexing the mapped reads

