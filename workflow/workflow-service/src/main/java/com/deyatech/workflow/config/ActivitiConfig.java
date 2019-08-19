package com.deyatech.workflow.config;//package com.deyatech.workflow.config;

import com.deyatech.workflow.listener.TaskOnCreateListener;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.StrongUuidGenerator;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ActivitiConfig {

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource,
            PlatformTransactionManager transactionManager) {
        SpringProcessEngineConfiguration configuration = new SpringProcessEngineConfiguration();
        configuration.setDataSource(dataSource);
        configuration.setTransactionManager(transactionManager);
        configuration.setDatabaseSchemaUpdate("true");
        configuration.setAsyncExecutorActivate(false);
        configuration.setJobExecutorActivate(false);
        configuration.setAsyncExecutorEnabled(false);
        configuration.setAsyncExecutorActivate(false);
        configuration.setIdGenerator(new StrongUuidGenerator());
        configuration.setJpaHandleTransaction(false);
        configuration.setJpaCloseEntityManager(false);
        configuration.setEnableProcessDefinitionInfoCache(false);
        configuration.setDeploymentMode("single-resource");
        configuration.setTypedEventListeners(typedEventListeners());
        return configuration;
    }

    @Bean
    public TaskOnCreateListener taskOnCreateListener() {
        return new TaskOnCreateListener();
    }

    private Map<String, List<ActivitiEventListener>> typedEventListeners() {
        Map<String, List<ActivitiEventListener>> eventListenerMap = new HashMap<>();

        eventListenerMap.put(ActivitiEventType.ENTITY_INITIALIZED.name(), Arrays.asList(
        ));

        eventListenerMap.put(ActivitiEventType.TIMER_FIRED.name(), Arrays.asList(


        ));
        eventListenerMap.put(ActivitiEventType.PROCESS_COMPLETED.name(), Arrays.asList(
        ));

        eventListenerMap.put(ActivitiEventType.PROCESS_CANCELLED.name(), Arrays.asList(

        ));

        eventListenerMap.put(ActivitiEventType.TASK_CREATED.name(), Arrays.asList(
                taskOnCreateListener()
        ));

        eventListenerMap.put(ActivitiEventType.TASK_COMPLETED.name(), Arrays.asList(
        ));

        return eventListenerMap;
    }
}
