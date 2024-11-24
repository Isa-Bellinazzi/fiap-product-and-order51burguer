```Mermaid
    graph TB
    subgraph Kubernetes Cluster
        style Kubernetes Cluster fill:#f9f9f9,stroke:#000,stroke-width:2px
        subgraph Namespace
            style Namespace fill:#e6e6e6,stroke:#000,stroke-width:2px
            subgraph Deployment-Burguer-app
                style Deployment-Burguer-app fill:#cce5ff,stroke:#007bff,stroke-width:2px
                Pod1[Pod 1]
                Pod2[Pod 2]
                Pod3[Pod 3]
            end
            subgraph Deployment-DB
                style Deployment-DB fill:#cce5ff,stroke:#007bff,stroke-width:2px
                DatabasePod[(Database Pod)]
            end
            AppService[Service burguer-app]
            style AppService fill:#d4edda,stroke:#155724,stroke-width:2px
            HPA[Horizontal Pod Autoscaler]
            style HPA fill:#fff3cd,stroke:#856404,stroke-width:2px
            ConfigMap[ConfigMap]
            style ConfigMap fill:#f8d7da,stroke:#721c24,stroke-width:2px
            DatabaseService[Service DB]
            style DatabaseService fill:#d4edda,stroke:#155724,stroke-width:2px
        end
        MetricsServer[Metrics Server]
        style MetricsServer fill:#e2e3e5,stroke:#383d41,stroke-width:2px
    end

    User[Usuário] -->|HTTP Request| AppService
    AppService --> Pod1
    AppService --> Pod2
    AppService --> Pod3
    HPA --> Deployment-Burguer-app
    HPA --> MetricsServer
    Pod1 --> ConfigMap
    Pod2 --> ConfigMap
    Pod3 --> ConfigMap
    Pod1 -->|SQL Queries| DatabaseService
    Pod2 -->|SQL Queries| DatabaseService
    Pod3 -->|SQL Queries| DatabaseService
    DatabaseService --> DatabasePod
    DatabasePod --> ConfigMap
    Deployment-Burguer-app --> ConfigMap
``` 