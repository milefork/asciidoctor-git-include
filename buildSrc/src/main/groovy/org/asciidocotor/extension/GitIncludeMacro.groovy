package org.asciidoctor.extension

import org.asciidoctor.extension.*;
import org.asciidoctor.ast.*;
 
import groovy.transform.CompileStatic;


/**
** USAGE: 
:git: path/to/git

include::git@path/to/source.java[repodir="path/where/.git/will/be",branch="master"]
**/

@CompileStatic
class GitIncludeMacro extends IncludeProcessor {

	private String repodir;
	private String branch;
	private String git;
	
	GitIncludeMacro(Map<String,Object> config) {
		super(config);
	}
	
	@Override
	public boolean handles(String target) {
	return target.startsWith("git@");
	}

	@Override
	public void process(DocumentRuby document, PreprocessorReader reader, String target, Map<String, Object> attributes) {
		Map<String, Object> at = document.getAttributes();
		String rep = (String)(attributes.get("repodir"));
		String br = (String)(attributes.get("branch"));
		String gi = (String)(at.get("git"));
		
		if(rep != null) {
			this.repodir = rep;
		}
		else {
			this.repodir = ".";
		}
		if(br != null) {
			this.branch = br;
		}
		else {
			this.branch="master";
		}
		if(gi != null) {
			this.git = gi;
		}
		else {
			throw new Exception("Please specify git path!")
		}
		StringBuilder content = readContent(target.replace("git@",""));
		reader.push_include(content.toString(), target, target, 1, attributes);

	}
	
	private StringBuilder readContent(String target) {
		StringBuilder content = new StringBuilder();
		content.append(this.branch);
		content.append(":");
		content.append(target);
		
		
		ProcessBuilder pb = new ProcessBuilder()
		pb.redirectErrorStream(true);
		
		pb.command(this.git,"--git-dir="+this.repodir+"/.git","show",content.toString());
		
		Process proc = pb.start();
		
		content.setLength(0);
		BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		
		String line = ""
		while (line != null) {
			line = br.readLine();
			content.append(line+"\r\n");
		}
		return content;
	}
	
}