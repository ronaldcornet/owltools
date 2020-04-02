import org.semanticweb.owlapi.owllink.builtin.response.Configuration;
import org.semanticweb.owlapi.owllink.builtin.response.ReasonerVersion;
import org.semanticweb.owlapi.owllink.builtin.response.Setting;
import org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration;

import java.util.Set;

/**
 * Author: Olaf Noppens
 */
public interface OWLlinkReasonerConfiguration {

    /**
     * Returns all Configurations.
     *
     * @return set of all Configurations.
     */
    Set<Configuration> getConfigurations();

    /** @return settings */
    Set<Setting> getSettings();

    /** @return reasoner version*/
    ReasonerVersion getReasonerVersion();

    // boolean set(Setting setting);

    /**
     * Returns the Configuration with the given key (if exists, otherwise returns {@code null}).
     *
     * @param key key
     * @return Configuration (or {@code null} if no configuration with the given key exists)
     */
    Configuration getConfiguration(String key);

    /**
     * Returns the {@link org.semanticweb.owlapi.reasoner.OWLReasonerConfiguration OWLReasonerConfiguration} used
     * to create OWLReasoners for KB in the server
     * @return OWLReasonerConfiguration
     */
    OWLReasonerConfiguration getOWLReasonerConfiguration();

}