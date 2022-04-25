package delphi.backend.nrxcodegeneration.repository;

import java.util.List;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import delphi.backend.nrxcodegeneration.model.NrxUser;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;

@Repository
public interface NrxUserRepository extends CrudRepository<NrxUser, Long> {

    /**
     * Find all Nrx User objects that have a username that matches the given username
     *
     * @param username The username of the user to find.
     * @return A list of NrxUser objects.
     */
    List<NrxUser> findByUsername(String username);

    /**
     * Find all the users with the given project names.
     *
     * @param users a list of strings that are the names of the users to find.
     * @return A list of users objects.
     */
    List<NrxUser> findByUsernameIn(List<String> users);
}
