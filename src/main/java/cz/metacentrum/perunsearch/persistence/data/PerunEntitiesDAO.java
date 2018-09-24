package cz.metacentrum.perunsearch.persistence.data;

import cz.metacentrum.perunsearch.persistence.models.entities.ExtSource;
import cz.metacentrum.perunsearch.persistence.models.entities.Facility;
import cz.metacentrum.perunsearch.persistence.models.entities.Group;
import cz.metacentrum.perunsearch.persistence.models.entities.Host;
import cz.metacentrum.perunsearch.persistence.models.entities.Member;
import cz.metacentrum.perunsearch.persistence.models.entities.Resource;
import cz.metacentrum.perunsearch.persistence.models.entities.Service;
import cz.metacentrum.perunsearch.persistence.models.entities.User;
import cz.metacentrum.perunsearch.persistence.models.entities.UserExtSource;
import cz.metacentrum.perunsearch.persistence.models.entities.Vo;

import java.util.List;

public interface PerunEntitiesDAO {

	List<ExtSource> getExtSources();

	List<Facility> getFacilities();

	List<Group> getGroups();

	List<Host> getHosts();

	List<Member> getMembers();

	List<Resource> getResources();

	List<Service> getServices();

	List<User> getUsers();

	List<UserExtSource> getUserExtSources();

	List<Vo> getVos();
}
