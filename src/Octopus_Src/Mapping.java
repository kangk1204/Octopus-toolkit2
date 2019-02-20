package Octopus_Src;

import java.io.File;
import java.io.FileWriter;

public class Mapping {
	private DataSet ds;
	private CommonFunc cf;
	private String genome;
	private String path;
	private String bamPath;
	private String fileName;
	private String spacePath;
	private String data;
	private boolean uncompress;

	public Mapping(DataSet ds, CommonFunc cf, String data) {
		this.ds = ds;
		this.cf = cf;
		this.data = data;
		genome = ds.getGSMInfo()[1];
		path = ds.getPath();
		spacePath = ds.getPath().replace(" ", "\\ ");
		bamPath = ds.getAnalysisPath() + "/02_Bam/";

		// Check Indexing
		checkIndexing();
		// unCompress
		unCompress();
	}

	public void unCompress() {
		uncompress = false;
		if (ds.getOmitStep("Trim") && data.equals("Private")) {
			if (ds.getForwardFastq().endsWith(".gz")) {
				if (ds.getReverseFastq().equals("")) {
					String cmd[] = { "gzip", "-k", "-d", ds.getForwardFastq() };
					cf.shellCmd(cmd);
					File file = new File(ds.getForwardFastq().replace(".gz", ""));
					File fileToMove = new File(ds.getAnalysisPath() + "00_Fastq/" + ds.getGSMInfo()[0] + ".fastq");
					boolean isMoved = file.renameTo(fileToMove);
					ds.setFastq(ds.getAnalysisPath() + "00_Fastq/" + ds.getGSMInfo()[0] + ".fastq", "");
				} else {
					String cmd1[] = { "gzip", "-k", "-d", ds.getForwardFastq() };
					String cmd2[] = { "gzip", "-k", "-d", ds.getReverseFastq() };
					cf.shellCmd(cmd1);
					cf.shellCmd(cmd2);

					File file = new File(ds.getForwardFastq().replace(".gz", ""));
					File fileToMove = new File(ds.getAnalysisPath() + "00_Fastq/" + ds.getGSMInfo()[0] + "_1.fastq");
					boolean isMoved = file.renameTo(fileToMove);

					file = new File(ds.getReverseFastq().replace(".gz", ""));
					fileToMove = new File(ds.getAnalysisPath() + "00_Fastq/" + ds.getGSMInfo()[0] + "_2.fastq");
					isMoved = file.renameTo(fileToMove);

					ds.setFastq(ds.getAnalysisPath() + "00_Fastq/" + ds.getGSMInfo()[0] + "_1.fastq",
							ds.getAnalysisPath() + "00_Fastq/" + ds.getGSMInfo()[0] + "_2.fastq");
				}
				uncompress = true;
			}
		}
	}

	public void Indexing() {
		// Check Reference

		String aligntool = "";
		if (ds.getGSMInfo()[2].toUpperCase().equals("RNA-SEQ")) {
			aligntool = ds.getAlignTools();
		} else {
			aligntool = "Hisat2";
		}

		ds.writeLogRun("Alignment : " + aligntool + "-build (Start)\n", true);
		ds.writeLogCmd("# Make the indexing file for mapping using the " + aligntool + ".\n");
		ds.getMainUI().setProgress(65, "Indexing : " + ds.getGSMInfo()[0]);

		System.out.print("Indexing -> ");
		if (!cf.checkReference("fasta", ds.getGSMInfo()[1])) { // exist:true,
																// Not
																// exist:false
			cf.downLoadReference("fasta", ds.getGSMInfo()[1]);
		}

		File dir = new File(path + "Index/" + aligntool + "/" + genome);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		if (aligntool.equals("Hisat2")) {

			// Ubuntu 18.04

			if (ds.getOS().equals("CentOS")) {
				String hisat2Idx_Cmd[] = { "python", path + "Tools/hisat2/hisat2-build", "-p", ds.getAnalysisCPU(),
						path + "Index/Reference/" + genome + ".fasta", path + "Index/Hisat2/" + genome + "/" + genome };
				String writeCmd = "python " + path + "Tools/hisat2/hisat2-build -p " + ds.getAnalysisCPU() + " " + path
						+ "Index/Reference/" + genome + ".fasta " + path + "Index/Hisat2/" + genome + "/" + genome;
				cf.shellCmd(hisat2Idx_Cmd);

				// Log
				ds.writeLogCmd(writeCmd + "\n\n");
			} else {

				String hisat2Idx_Cmd[] = { "python3", path + "Tools/hisat2/hisat2-build", "-p", ds.getAnalysisCPU(),
						path + "Index/Reference/" + genome + ".fasta", path + "Index/Hisat2/" + genome + "/" + genome };
				String writeCmd = "python3 " + path + "Tools/hisat2/hisat2-build -p " + ds.getAnalysisCPU() + " " + path
						+ "Index/Reference/" + genome + ".fasta " + path + "Index/Hisat2/" + genome + "/" + genome;
				cf.shellCmd(hisat2Idx_Cmd);

				// Log
				ds.writeLogCmd(writeCmd + "\n\n");
			}

		} else {
			String starIdx_Cmd[] = { path + "Tools/STAR/bin/Linux_x86_64/STAR", "--runMode", "genomeGenerate",
					"--genomeDir", path + "Index/STAR/" + genome, "--genomeFastaFiles",
					path + "Index/Reference/" + genome + ".fasta", "--runThreadN", ds.getAnalysisCPU(),
					"--sjdbOverhang", "100", "--sjdbGTFfile", path + "Index/Reference/" + genome + ".gtf" };

			String writeCmd = path + "Tools/STAR/bin/Linux_x86_64/STAR --runMode genomeGenerate --genomeDir " + path
					+ "Index/STAR/" + genome + " --genomeFastaFiles " + path + "Index/Reference/" + genome
					+ ".fasta --runThreadN " + ds.getAnalysisCPU() + " --sjdbOverhang 100 --sjdbGTFfile " + path
					+ "Index/Reference/" + genome + ".gtf";
			cf.shellCmd(starIdx_Cmd);

			// Log
			ds.writeLogCmd(writeCmd + "\n\n");

		}
		ds.writeLogRun("Alignment : " + aligntool + "-build (End)\n", true);
	}

	public void Mapping() {

		String aligntool = "";
		if (ds.getGSMInfo()[2].toUpperCase().equals("RNA-SEQ")) {
			aligntool = ds.getAlignTools();
		} else {
			aligntool = "Hisat2";
		}

		ds.writeLogCmd("# Align to the genome using the " + aligntool + "\n");
		ds.writeLogRun("Alignment : " + aligntool + "-align (Start)\n", true);
		ds.getMainUI().setProgress(70, "Mapping : " + ds.getGSMInfo()[0]);

		String bam = "";
		String encoding = "";
		System.out.print("Mapping -> ");

		try {
			FileWriter fw = new FileWriter(ds.getPath() + "Script/Mapping.sh");
			String align_Cmd = "";

			File tmpF = new File(ds.getForwardFastq());
			String strand = "";

			// Phred score
			if (ds.getEncodingFr().equals("sanger")) {
				encoding = "33";
			} else {
				encoding = "64";
			}
			fileName = cf.makeLabel(false, data);

			String fr = ds.getForwardFastq().replace(" ", "\\ ");

			if (aligntool.equals("Hisat2")) {
				fileName = fileName + ".ht2";
				String fullOption = ds.getToolOPtion(4); // 4 : hisat-align

				// Strand
				if (data.equals("Public")) {
					if (ds.getPublicStrand().equals("FR-Firststrand")) {
						strand = "--fr";
					} else if (ds.getPublicStrand().equals("FR_Secondstrand")) {
						strand = "--rf";
					}
				} else { // Private
					if (ds.getGSMInfo()[5].equals("FR-Firststrand")) {
						strand = "--fr";
					} else if (ds.getGSMInfo()[5].equals("FR_Secondstrand")) {
						strand = "--rf";
					}
				}

				if (fr.endsWith(".gz")) {
					if (ds.getFullOption()) {
						if (ds.getReverseFastq().equals("")) {
							align_Cmd = spacePath + "Tools/hisat2/hisat2 -p " + ds.getAnalysisCPU() + " --phred"
									+ encoding + " " + fullOption + " -x " + spacePath + "Index/Hisat2/" + genome + "/"
									+ genome + " -U <(zcat " + ds.getForwardFastq().replace(" ", "\\ ") + ") | "
									+ spacePath + "Tools/Samtools/samtools view -bS -> " + bamPath.replace(" ", "\\ ")
									+ fileName + ".bam";
						} else {
							align_Cmd = spacePath + "Tools/hisat2/hisat2 -p " + ds.getAnalysisCPU() + " --phred"
									+ encoding + " " + strand + " " + fullOption + " -x " + spacePath + "Index/Hisat2/"
									+ genome + "/" + genome + " -1 <(zcat " + ds.getForwardFastq().replace(" ", "\\ ")
									+ ") -2 <(zcat " + ds.getReverseFastq().replace(" ", "\\ ") + ") | " + spacePath
									+ "Tools/Samtools/samtools view -bS -> " + bamPath.replace(" ", "\\ ") + fileName
									+ ".bam";
						}
					} else {
						if (ds.getReverseFastq().equals("")) {
							align_Cmd = spacePath + "Tools/hisat2/hisat2 -p " + ds.getAnalysisCPU() + " --phred"
									+ encoding + " -x " + spacePath + "Index/Hisat2/" + genome + "/" + genome
									+ " -U <(zcat " + ds.getForwardFastq().replace(" ", "\\ ") + ") | " + spacePath
									+ "Tools/Samtools/samtools view -bS -> " + bamPath.replace(" ", "\\ ") + fileName
									+ ".bam";
						} else {
							align_Cmd = spacePath + "Tools/hisat2/hisat2 -p " + ds.getAnalysisCPU() + " --phred"
									+ encoding + " " + strand + " -x " + spacePath + "Index/Hisat2/" + genome + "/"
									+ genome + " -1 <(zcat " + ds.getForwardFastq().replace(" ", "\\ ") + ") -2 <(zcat "
									+ ds.getReverseFastq().replace(" ", "\\ ") + ") | " + spacePath
									+ "Tools/Samtools/samtools view -bS -> " + bamPath.replace(" ", "\\ ") + fileName
									+ ".bam";
						}
					}
				} else {
					if (ds.getFullOption()) {
						if (ds.getReverseFastq().equals("")) {
							align_Cmd = spacePath + "Tools/hisat2/hisat2 -p " + ds.getAnalysisCPU() + " --phred"
									+ encoding + " " + fullOption + " -x " + spacePath + "Index/Hisat2/" + genome + "/"
									+ genome + " -U " + ds.getForwardFastq().replace(" ", "\\ ") + " | " + spacePath
									+ "Tools/Samtools/samtools view -bS -> " + bamPath.replace(" ", "\\ ") + fileName
									+ ".bam";
						} else {
							align_Cmd = spacePath + "Tools/hisat2/hisat2 -p " + ds.getAnalysisCPU() + " --phred"
									+ encoding + " " + strand + " " + fullOption + " -x " + spacePath + "Index/Hisat2/"
									+ genome + "/" + genome + " -1 " + ds.getForwardFastq().replace(" ", "\\ ") + " -2 "
									+ ds.getReverseFastq().replace(" ", "\\ ") + " | " + spacePath
									+ "Tools/Samtools/samtools view -bS -> " + bamPath.replace(" ", "\\ ") + fileName
									+ ".bam";
						}
					} else {
						if (ds.getReverseFastq().equals("")) {
							align_Cmd = spacePath + "Tools/hisat2/hisat2 -p " + ds.getAnalysisCPU() + " --phred"
									+ encoding + " -x " + spacePath + "Index/Hisat2/" + genome + "/" + genome + " -U "
									+ ds.getForwardFastq().replace(" ", "\\ ") + " | " + spacePath
									+ "Tools/Samtools/samtools view -bS -> " + bamPath.replace(" ", "\\ ") + fileName
									+ ".bam";
						} else {
							align_Cmd = spacePath + "Tools/hisat2/hisat2 -p " + ds.getAnalysisCPU() + " --phred"
									+ encoding + " " + strand + " -x " + spacePath + "Index/Hisat2/" + genome + "/"
									+ genome + " -1 " + ds.getForwardFastq().replace(" ", "\\ ") + " -2 "
									+ ds.getReverseFastq().replace(" ", "\\ ") + " | " + spacePath
									+ "Tools/Samtools/samtools view -bS -> " + bamPath.replace(" ", "\\ ") + fileName
									+ ".bam";
						}
					}
				}
			} else {
				// STAR
				fileName = fileName + ".str";
				String fullOption = ds.getToolOPtion(5); // 5 : STAR
				// Strand
				if (data.equals("Public")) {
					if (ds.getPublicStrand().equals("Unstrand")) {
						strand = "--outSAMstrandField intronMotif";
					} else {
						strand = "";
					}
				} else { // Private
					if (ds.getGSMInfo()[5].equals("Unstrand")) {
						strand = "--outSAMstrandField intronMotif";
					} else {
						strand = "";
					}
				}
				if (fr.endsWith(".gz")) {
					if (ds.getFullOption()) {
						if (ds.getReverseFastq().equals("")) {
							align_Cmd = spacePath + "Tools/STAR/bin/Linux_x86_64/STAR --genomeDir " + spacePath
									+ "Index/STAR/" + genome + "/ --runThreadN " + ds.getAnalysisCPU() + " " + strand
									+ " --outSAMtype BAM Unsorted " + fullOption + " --outFileNamePrefix "
									+ bamPath.replace(" ", "\\ ") + fileName + " --readFilesIn <(zcat "
									+ ds.getForwardFastq().replace(" ", "\\ ") + ")";
						} else {
							align_Cmd = spacePath + "Tools/STAR/bin/Linux_x86_64/STAR --genomeDir " + spacePath
									+ "Index/STAR/" + genome + "/ --runThreadN " + ds.getAnalysisCPU() + " " + strand
									+ " --outSAMtype BAM Unsorted " + fullOption + " --outFileNamePrefix "
									+ bamPath.replace(" ", "\\ ") + fileName + " --readFilesIn <(zcat "
									+ ds.getForwardFastq().replace(" ", "\\ ") + ") <(zcat "
									+ ds.getReverseFastq().replace(" ", "\\ ") + ")";
						}
					} else {
						if (ds.getReverseFastq().equals("")) {
							align_Cmd = spacePath + "Tools/STAR/bin/Linux_x86_64/STAR --genomeDir " + spacePath
									+ "Index/STAR/" + genome + "/ --runThreadN " + ds.getAnalysisCPU() + " " + strand
									+ " --outSAMtype BAM Unsorted --outFileNamePrefix " + bamPath.replace(" ", "\\ ")
									+ fileName + " --readFilesIn <(zcat " + ds.getForwardFastq().replace(" ", "\\ ")
									+ ")";
						} else {
							align_Cmd = spacePath + "Tools/STAR/bin/Linux_x86_64/STAR --genomeDir " + spacePath
									+ "Index/STAR/" + genome + "/ --runThreadN " + ds.getAnalysisCPU() + " " + strand
									+ " --outSAMtype BAM Unsorted --outFileNamePrefix " + bamPath.replace(" ", "\\ ")
									+ fileName + " --readFilesIn <(zcat " + ds.getForwardFastq().replace(" ", "\\ ")
									+ ") <(zcat " + ds.getReverseFastq().replace(" ", "\\ ") + ")";
						}
					}
				} else {
					if (ds.getFullOption()) {
						if (ds.getReverseFastq().equals("")) {
							align_Cmd = spacePath + "Tools/STAR/bin/Linux_x86_64/STAR --genomeDir " + spacePath
									+ "Index/STAR/" + genome + "/ --runThreadN " + ds.getAnalysisCPU() + " " + strand
									+ " --outSAMtype BAM Unsorted " + fullOption + " --outFileNamePrefix "
									+ bamPath.replace(" ", "\\ ") + fileName + " --readFilesIn "
									+ ds.getForwardFastq().replace(" ", "\\ ");
						} else {
							align_Cmd = spacePath + "Tools/STAR/bin/Linux_x86_64/STAR --genomeDir " + spacePath
									+ "Index/STAR/" + genome + "/ --runThreadN " + ds.getAnalysisCPU() + " " + strand
									+ " --outSAMtype BAM Unsorted " + fullOption + " --outFileNamePrefix "
									+ bamPath.replace(" ", "\\ ") + fileName + " --readFilesIn "
									+ ds.getForwardFastq().replace(" ", "\\ ") + " "
									+ ds.getReverseFastq().replace(" ", "\\ ");
						}
					} else {
						if (ds.getReverseFastq().equals("")) {
							align_Cmd = spacePath + "Tools/STAR/bin/Linux_x86_64/STAR --genomeDir " + spacePath
									+ "Index/STAR/" + genome + "/ --runThreadN " + ds.getAnalysisCPU() + " " + strand
									+ " --outSAMtype BAM Unsorted --outFileNamePrefix " + bamPath.replace(" ", "\\ ")
									+ fileName + " --readFilesIn " + ds.getForwardFastq().replace(" ", "\\ ");
						} else {
							align_Cmd = spacePath + "Tools/STAR/bin/Linux_x86_64/STAR --genomeDir " + spacePath
									+ "Index/STAR/" + genome + "/ --runThreadN " + ds.getAnalysisCPU() + " " + strand
									+ " --outSAMtype BAM Unsorted --outFileNamePrefix " + bamPath.replace(" ", "\\ ")
									+ fileName + " --readFilesIn " + ds.getForwardFastq().replace(" ", "\\ ") + " "
									+ ds.getReverseFastq().replace(" ", "\\ ");
						}
					}
				}
			}

			fw.write(align_Cmd + "\n");
			if (aligntool.equals("STAR")) {
				fw.write("mv " + bamPath + fileName + "Aligned.out.bam " + bamPath + fileName + ".bam\n");
			}

			fw.flush();

			// Log
			ds.writeLogCmd(align_Cmd + "\n\n");

			fw.close();
			String cmdHisat2_1[] = { "chmod", "777", ds.getPath() + "/Script/Mapping.sh" };
			String cmdHisat2_2[] = { "sh", ds.getPath() + "/Script/Mapping.sh" };

			cf.shellCmd(cmdHisat2_1);
			cf.shellCmd(cmdHisat2_2);

			bam = bamPath + fileName + ".bam";
			ds.setBamFile(bam);
			ds.setLabelTitle(fileName);

			String size[] = { "du", "-b", bam };
			String value = cf.shellCmd(size);
			String tmp[] = value.split("\t");

			if (Long.parseLong(tmp[0]) / 1000 / 1000 < 2) {
				ds.getErrLog().setStepErr(4, true);
			}

		} catch (Exception e) {
			ds.getErrLog().setStepErr(4, true);
			return;
		}

		if (uncompress == true) {
			if (ds.getReverseFastq().equals("")) {
				new File(ds.getAnalysisPath() + "00_Fastq/" + ds.getGSMInfo()[0] + ".fastq").delete();
			} else {
				new File(ds.getAnalysisPath() + "00_Fastq/" + ds.getGSMInfo()[0] + "_1.fastq").delete();
				new File(ds.getAnalysisPath() + "00_Fastq/" + ds.getGSMInfo()[0] + "_2.fastq").delete();
			}
		}

		// Remove File
		if (ds.getOmitStep("Trim")) {
			if (ds.getRemove(1)) {
				cf.removeFile("Fastq");
			} else {
				if (ds.getCompressGz() == true) {
					cf.compressGz("Fastq", data);
				}
			}

		} else { // Running Trim
			if (ds.getRemove(3)) {
				cf.removeFile("Trim");
			} else {
				if (ds.getCompressGz() == true) {
					cf.compressGz("Trim", data);
				}

			}
		}

		// compress file

		ds.writeLogRun("Alignment : " + aligntool + "-align (End)\n", true);
	}

	public void Sorting() {
		ds.writeLogCmd("# Sort the mapped sequence file using the Samtools\n");
		ds.writeLogRun("Sorting : Samtools (Start)\n", true);
		ds.getMainUI().setProgress(80, "Sorting : " + ds.getGSMInfo()[0]);

		String bam = "";

		System.out.print("Sorting -> ");
		try {
			String samtoolSort_Cmd[] = { path + "Tools/Samtools/samtools", "sort", ds.getBamFile(), "-@",
					ds.getAnalysisCPU(), "-o", bamPath + "sorted_" + fileName + ".bam" };
			String samtoolIndex_Cmd[] = { path + "Tools/Samtools/samtools", "index",
					bamPath + "sorted_" + fileName + ".bam" };

			cf.shellCmd(samtoolSort_Cmd);
			cf.shellCmd(samtoolIndex_Cmd);

			// Log
			ds.writeLogCmd(path + "Tools/Samtools/samtools sort " + ds.getBamFile() + " -@ " + ds.getAnalysisCPU()
					+ " -o " + bamPath + "sorted_" + fileName + ".bam\n");
			ds.writeLogCmd(path + "Tools/Samtools/samtools index " + bamPath + "sorted_" + fileName + ".bam\n\n");

			String size[] = { "du", "-b", bamPath + "sorted_" + fileName + ".bam" };
			String value = cf.shellCmd(size);
			String tmp[] = value.split("\t");
			if (Long.parseLong(tmp[0]) / 1000 / 1000 < 2) {
				ds.getErrLog().setStepErr(5, true);
			}

			// Remove File
			if (ds.getRemove(4)) {
				cf.removeFile("Bam");
			}

			// Add DataSet : Sorted_Bam Locus & FileName;
			bam = bamPath + "sorted_" + fileName + ".bam";
			fileName = "sorted_" + fileName;
			ds.setBamFile(bam);

			new File(ds.getPath() + "Script/Sorting.sh").delete();

			// Convert bam to Cram
			if (ds.getCompressCram() == true) {
				bamToCram();
			}

		} catch (Exception e) {
			ds.getErrLog().setStepErr(5, true);
		}
		ds.writeLogRun("Sorting : Samtools (End)\n", true);
	}

	public void checkIndexing() {

		String aligntool = "";
		if (ds.getGSMInfo()[2].toUpperCase().equals("RNA-SEQ")) {
			aligntool = ds.getAlignTools();
		} else {
			aligntool = "Hisat2";
		}

		String addressIdx = path + "Index/" + aligntool + "/" + genome;

		File indexFolder = new File(addressIdx);
		File[] list = indexFolder.listFiles();

		if (list == null) {
			Indexing();
		} else {

			if (aligntool.equals("Hisat2") && !(list.length >= 8))
				Indexing();
			if (aligntool.equals("STAR") && !(list.length >= 15))
				Indexing();

		}
	}

	public void bamToCram() {
		String bamToCram_Cmd[] = { path + "Tools/Samtools/samtools", "view", "-C", "-T",
				path + "Index/Reference/" + genome + ".fasta", ds.getBamFile(), "-@", ds.getAnalysisCPU(), "-o",
				bamPath + fileName + ".cram" };
		cf.shellCmd(bamToCram_Cmd);
	}
}