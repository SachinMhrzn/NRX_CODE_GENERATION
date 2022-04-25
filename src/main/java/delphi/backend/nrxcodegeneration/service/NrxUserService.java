package delphi.backend.nrxcodegeneration.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import delphi.backend.nrxcodegeneration.model.NrxUser;
import delphi.backend.nrxcodegeneration.constant.NrxCodeConstant;
import delphi.backend.nrxcodegeneration.repository.NrxUserRepository;
import delphi.backend.nrxcodegeneration.foundation.error.exception.BadRequestException;

@Singleton
public class NrxUserService {

    @Inject
    private NrxUserRepository nrxUserRepository;

    /**
     * Find nrx user by username and throw an error if not found.
     *
     * @param username The username of the user to find.
     * @return NrxUser.
     * @throws BadRequestException If the user with given username is not found.
     */
    public NrxUser findNrxUserByUsernameThrowErrorIfNotFound(String username) {
        return nrxUserRepository.findByUsername(username)
                .stream()
                .findFirst()
                .orElseThrow(() -> new BadRequestException(String.format(NrxCodeConstant.NOT_FOUND, "User")));
    }
}
