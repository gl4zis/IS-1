## Quickstart

1. Download wildfly full profile server (I deploy on 27.0.1)
2. Add datasource for postgres DB named ``java:jboss/datasources/postgres``
3. Add file ``secret.properties`` to resources dir
4. Add ``jwt.access.key`` property (any sting, secret key for token generation)
5. Deploy ``ROOT.war`` on server
6. If tables won't be created automatically change ``hibernate.hbm2ddl.auto`` property
to ``create`` in ``resources/META-INF/persistence.xml``


- Controllers mapped on ``/api`` url
- Request/responses formats described in ``dto`` package
- All urls which start **NOT** from ``/api/auth`` need bearer jwt token in ``Authorization``
header, like: ``Authorization: Bearer <some_token>``