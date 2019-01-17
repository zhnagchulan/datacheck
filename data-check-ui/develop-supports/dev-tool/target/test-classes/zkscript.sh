#!/usr/bin/env bash
. /Library/Java_runtime/zookeeper-3.4.9/bin/zkCli.sh <<!!!
create /example ''
create /example/db ''
create /example/db/version ''
create /example/db/version/global 1.1.0-SNAPSHOT
create /example/serviceapp ''
create /example/serviceapp/config ''
create /example/serviceapp/version ''
create /example/serviceapp/version/global 1.1.0-SNAPSHOT
create /example/serviceapp/heartbeat ''
create /example/webapp ''
create /example/webapp/config ''
create /example/webapp/version ''
create /example/webapp/version/global 1.1.0-SNAPSHOT
create /example/webapp/heartbeat ''
create /example/batchapp ''
create /example/batchapp/config ''
create /example/batchapp/version ''
create /example/batchapp/version/global 1.1.0-SNAPSHOT
create /example/batchapp/heartbeat ''
close
!!!
