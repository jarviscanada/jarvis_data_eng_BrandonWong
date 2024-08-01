# Linux Cluster Monitoring Agent
## Introduction
This project is an automated system that keeps track of the resources utilized by a Linux Cluster. It tracks the cpu, memory and disk usage of the given machine and persists all this information within a dedicated database. It is written for system admins or developers who manage actual/virtual Linux machines.
### Technologies
- **Bash:** Write scripts to fetch information of the given machine
- **Docker:** Run a Postgres container and maintain its volume
- **Git:** Keep track of project changes
- **Postgres:** Persists the machine's information
- **Rocky 9:** OS environment that was written it on

## Quick Start
To spin up this project on local/remote machine
1. Clone this repository
```
cd $HOME
git clone https://github.com/jarviscanada/jarvis_data_eng_BrandonWong.git
```
2. Start a psql instance using psql_docker.sh
```
chmod u+x $HOME/linux_sql/scripts/psql_docker.sh
./$HOME/linux_sql/scripts/psql_docker.sh <cmd> <db_username> <db_password>
```
3. Insert host info to the database
```
chmod u+x $HOME/linux_sql/scripts/host_info.sh
./$HOME/linux_sql/scripts/host_info.sh <psql_host> <psql_port> <db_name> <psql_user> <psql_password>
```
4. Setup crontab to update host usage every minute
```
chmod u+x $HOME/linux_sql/scripts/usage.sh

crontab -e

# Inside crontab in the machine's default editor
* * * * * $HOME/linux_sql/scripts/host_usage.sh <psql_host> <psql_port> <db_name> <psql_user> <psql_password>
```

## Implementation
### Architecture
### Scripts
### Database Modeling
The database has two tables where we keep track of each host machine and each new log is associated with such machine.
### `host_info`
- `id`: Unique ID of host machine
- `hostname`: Unique name of the machine
- `cpu_number`: Number of CPU cores
- `cpu_architecture`: Type of CPU architecture, e.g., aarch64, x86_64
- `cpu_model`: Model of the CPU
- `cpu_mhz`: Refresh Rate of a single CPU core
- `l2_cache`: Size of the L2 cache
- `timestamp`: Date snapshot when the information was read
- `total_mem`: Total size of memory
**NOTE: `id` is the primary key and both `id` and `hostname` must be unique**
### `host_usage`
- `timestamp`: Date snapshot when the information was read
- `host_id`: Unique ID of host machine that relates to `id` under the `host_info` table
- `memory_free`: Available unused memory
- `cpu_idle`: Percentage of the CPU in idle
- `cpu_kernel`: Percentage of the CPU doing a system task
- `disk_io`: Number of tasks being read/write
- `disk_available`: Total size of available storage
**NOTE: `host_id` is the foreign key that refers to the `host_info` table**

## Test
Manual testing was used to test the bash scripts DDL following Base, Alternative and Error cases. Here are the following cases for each script.
### `psql_docker.sh`
#### Base
- Create a new Postgres container
- Start an existing Postgres container
- Stop an existing Postgres container
#### Error
- Create a new Postgres container while an existing one is running
- Start a non-existing Postgres container
- Stop a non-existing Postgres container
- Create a Postgres container with missing arguments
- Start a Postgres container with more arguments
- Stop a Postgres container with more arguments
- Use a command that does not exist
### `host_info.sh`
#### Base
- Add host info to the Postgres container
#### Error
- Add host info that already exists
- Provide invalid number of arguments
### `host_usage.sh`
#### Base
- Add host usage from existing host to Postgres container
#### Error
- Add host usage from non-existing host to Postgres container
- Provide invalid number of arguments

## Deployment
The application is deployed using both **Docker** and `crontab`. Docker is used to run the **Postgres** in an isolated environment. `crontab` is used to run the `host_usage.sh` script **every minute** on host machine.

## Improvements
- Write a test suite that can handle all use cases of the system. For example, writing unit tests to handle Normal, Edge and Error cases; and Integration tests to validate the entire flow. These measures will help future add-on to this project while ensuring the previous functionality remains the same.
- Write a setup script so that the user can be abstracted from setting this entire project up and focus on using it for their own purposes.
- Write different scripts to handle different distros since each company/developer has their preferred distro and this project can be OS agnostic.
