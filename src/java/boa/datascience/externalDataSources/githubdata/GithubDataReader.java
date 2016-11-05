package boa.datascience.externalDataSources.githubdata;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.protobuf.GeneratedMessage;

import boa.datascience.externalDataSources.AbstractDataReader;
import boa.datascience.externalDataSources.gitdata.GitDataReader;
//import boa.datascience.externalDataSources.gitdata.GitDataReader;
//import boa.datascience.externalDataSources.githubdata.Githubschema.CodeRepository;
//import boa.datascience.externalDataSources.githubdata.Githubschema.Person;
//import boa.datascience.externalDataSources.githubdata.Githubschema.Project;
//import boa.datascience.externalDataSources.githubdata.Githubschema.Project.ForgeKind;
import boa.datascience.externalDataSources.httpdata.HttpDataReader;
import boa.types.Code.CodeRepository;
import boa.types.Shared.Person;
import boa.types.Toplevel.Project;
import boa.types.Toplevel.Project.ForgeKind;

/**
 * Created by nmtiwari on 11/2/16.
 */
public class GithubDataReader extends AbstractDataReader {

	// FIXME: This should be a pattern of github urls
	private final String GITHUBURL = "https://github.com/";
	// private final String GITHUBPARSERCLASS =
	// "boa.datascience.externalDataSources.githubdata.Githubschema.Project";
	private final String GITHUBPARSERCLASS = "boa.types.Toplevel.Project";

	public GithubDataReader(String source) {
		super(source);
	}

	/**
	 *
	 * @param source
	 *            url
	 * @return if the url is github url or not.
	 */
	@Override
	public boolean isReadable(String source) {
		UrlValidator urlValidator = new UrlValidator();
		return urlValidator.isValid(source) && source.startsWith(GITHUBURL);
	}

	@Override
	public List<GeneratedMessage> getData() {
		List<GeneratedMessage> list = new ArrayList<GeneratedMessage>();
		com.google.protobuf.GeneratedMessage data = null;
		String[] details = this.dataSource.split("/");
		String dataurl = "https://api.github.com/repos/" + details[details.length - 2] + "/"
				+ details[details.length - 1];
		HttpDataReader httpDataCollector = new HttpDataReader(dataurl);
		String username = "nmtiwari";
		String password = "swanit*49912";
		if ("password".equals(password)) {
			throw new IllegalArgumentException("Your username password in file GithubDataReaser.java are not yet set");
		}
		if (httpDataCollector.authenticate(username, password)) {
			httpDataCollector.getResponseJson();
			String response = httpDataCollector.getContent();
			data = buildData(response);
		}
		list.add(data);
		return list;
	}

	public GeneratedMessage buildData(String data) {
		JSONObject projectJ = new JSONObject(data);
		boa.types.Toplevel.Project.Builder projectB = Project.newBuilder();
		projectB.setId(projectJ.get("id").toString());
		projectB.setName(projectJ.getString("name"));
		projectB.setProjectUrl(projectJ.getString("html_url"));
		projectB.setKind(ForgeKind.GITHUB);
		if (projectJ.has("homepage_url"))
			projectB.setHomepageUrl(projectJ.getString("homepage_url"));
		if (projectJ.has("created_date"))
			projectB.setCreatedDate(projectJ.getLong("created_date"));
		if (projectJ.has("description"))
			projectB.setDescription(projectJ.getString("description"));
		if (projectJ.has("donations"))
			projectB.setDonations(projectJ.getBoolean("donations"));

		JSONArray seq = null;
		if (projectJ.has("operating_systems")) {
			seq = projectJ.getJSONArray("operating_systems");
			for (Object seqEle : seq) {
				projectB.addOperatingSystems(seqEle.toString());
			}
		}

		if (projectJ.has("languages_url")) {
			projectB.addProgrammingLanguages(projectJ.getString("languages_url"));
		}

		if (projectJ.has("databases")) {
			seq = projectJ.getJSONArray("databases");
			for (Object seqEle : seq) {
				projectB.addDatabases(seqEle.toString());
			}
		}

		if (projectJ.has("licenses")) {
			seq = projectJ.getJSONArray("licenses");
			for (Object seqEle : seq) {
				projectB.addLicenses(seqEle.toString());
			}
		}

		if (projectJ.has("interfaces")) {
			seq = projectJ.getJSONArray("interfaces");
			for (Object seqEle : seq) {
				projectB.addInterfaces(seqEle.toString());
			}
		}

		if (projectJ.has("audiences")) {
			seq = projectJ.getJSONArray("audiences");
			for (Object seqEle : seq) {
				projectB.addAudiences(seqEle.toString());
			}
		}

		if (projectJ.has("topics")) {
			seq = projectJ.getJSONArray("topics");
			for (Object seqEle : seq) {
				projectB.addTopics(seqEle.toString());
			}
		}

		if (projectJ.has("status")) {
			seq = projectJ.getJSONArray("status");
			for (Object seqEle : seq) {
				projectB.addStatus(seqEle.toString());
			}

		}

		if (projectJ.has("translations")) {
			seq = projectJ.getJSONArray("translations");
			for (Object seqEle : seq) {
				projectB.addTranslations(seqEle.toString());
			}

		}

		if (projectJ.has("maintainers")) {
			seq = projectJ.getJSONArray("maintainers");
			for (Object seqEle : seq) {
				Person.Builder person = Person.newBuilder();
				JSONObject obj = (JSONObject) seqEle;
				person.setUsername(obj.getString("username"));
				person.setRealName(obj.getString("real_name"));
				person.setEmail(obj.getString("email"));
				projectB.addMaintainers(person.build());
			}

		}

		if (projectJ.has("owner")) {
			JSONObject owner = projectJ.getJSONObject("owner");
			Person.Builder person = Person.newBuilder();
			person.setUsername(owner.getString("login"));
			person.setRealName(owner.getString("login"));
			person.setEmail(owner.getString("url"));
		}

		if (projectJ.has("developers")) {
			projectJ.getJSONArray("developers");
			for (Object seqEle : seq) {
				Person.Builder person = Person.newBuilder();
				JSONObject obj = (JSONObject) seqEle;
				person.setUsername(obj.getString("username"));
				person.setRealName(obj.getString("real_name"));
				person.setEmail(obj.getString("email"));
				projectB.addDevelopers(person.build());
			}

		}

		if (projectJ.has("clone_url")) {
			for (GeneratedMessage code : new GitDataReader(projectJ.getString("clone_url")).getData()) {
				projectB.addCodeRepositories((CodeRepository) code);
			}
			// projectB.addCodeRepositories(projectJ.getString("clone_url").toString());
		}
		// if (projectJ.has("issues_url")) {
		// projectB.addIssueRepositories(projectJ.getString("issues_url").toString());
		// }
		return projectB.build();
	}

	@Override
	public String getParserClassName() {
		return GITHUBPARSERCLASS;
	}
}