# JBoss Wildfly Cluster Setup via viagrant/chef

## Requirements

* Chef 10.12+ (https://downloads.chef.io/chef-client/) and Development Kit (https://downloads.chef.io/chef-dk)
* Vagrant 1.4.3+ (https://www.vagrantup.com/downloads.html)
* Virtualbox (https://www.virtualbox.org/wiki/Downloads)

## Usage

  With Vagrant:

  ```bash
  cd wildfly-clu && vagrant up
  ```
  With Chef:

  ```bash
  add [ wildfly-clu:default ,wildfly-clu:logs, wildfly:domain ]

  ```
  in your run list:

  ```bash
  "recipe[java]",
  "recipe[wildfly-clu::default]",
  "recipe[wildfly-clu::logs]",
  "recipe[wildfly-clu::domain]"  
  ```

  Put these in yout hosts file:

  ```bash
  33.33.33.111 myserver1
  33.33.33.112 myserver2
  33.33.33.113 myserver3
    ```

After the installation connect to:

  * http://myserver1:9990/console  (WildFly admin console)
  * http://myserver1:22002         (Haproxy admin console)

Point directly to the back-ends

  * http://myserver1:8080/
  * http://myserver2:8080/
  * http://myserver3:8080/

### Attributes
  User, group and application name ( app name will be /usr/local/myapplication and /etc/init.d/myapplication )
```
# username
default['wildfly-clu']['user']  = "wildfly"
# unix group
default['wildfly-clu']['group'] = "wildfly"
# name of the application, will get /usr/local/wildfly (linked to /usr/local/wildfly-8.8.8-Final)
default['wildfly-clu']['name']  = "wildfly"
# unix service name
default['wildfly-clu']['wildfly']['service'] = "wildfly"

# wildfly options
default['wildfly-clu']['wildfly']['version'] = "8.0.0"
default['wildfly-clu']['wildfly']['url']="http://download.jboss.org/wildfly/8.0.0.Final/wildfly-8.0.0.Final.tar.gz"
default['wildfly-clu']['wildfly']['checksum']='7316100a8dae90a74fb96f9d70d758daee71ebd70d5ed680307082f010d6f3a9'
default['wildfly-clu']['wildfly']['targz'] = "wildfly-#{node['wildfly-clu']['wildfly']['version']}.Final.tar.gz"
default['wildfly-clu']['wildfly']['home']="/usr/local/#{node['wildfly-clu']['name']}"
default['wildfly-clu']['wildfly']['base'] = "/usr/local"
default['wildfly-clu']['wildfly']['logs'] = "/usr/local/#{node['wildfly-clu']['name']}/standalone/log"

# Deploy options

#Set true to deploy the HelloWorld.war
default['wildfly-clu']['wildfly']['deploy_hello_world'] = true

#set this to true in order to configure an haproxy for the slaves declared in the cluster schema
default['wildfly-clu']['wildfly']['haproxy'] = true

#password for the slave realm.. use encrypted databag for increase security
default['wildfly-clu']['wildfly']['ManagementRealm'] = "a2VybmVscGFuaWMhMTIz"
```
     
### Login-Details

* Default credential for Wildfly console:
	* username:  admin
	* password:  admin
* Default username for slave-master communication:
   	* username:  slave
    * password:  kernelpanic!123

**Cluster definition**

```javascript
default['wildfly-clu']['cluster_schema'] = {
	"myserver1"  => { 
    	:role => "domain-controller" ,
        :ip => "33.33.33.111",
        :port_offset => "0" },
    "myserver2"  => {
    	:role => "slave",
        :ip => "33.33.33.112" ,
        :master => "myserver1" ,
        :port_offset => "0" },
	"myserver3"  => {
        :role => "slave"  ,
        :ip => "33.33.33.113" ,
        :master => "myserver1" ,
    	:port_offset => "0" }
}
```



### Recipes
 default => install Wildfly

 domain  => configure your cluster

 haproxy => install haproxy to see quickly the cluster works

 logs => configure log rotation

### Troubleshooting

Berkshalf vagrant variant has been renamed by its maintainer at
https://github.com/RiotGames/vagrant-berkshelf
If you getting this error:

```bash
Unknown configuration section 'berkshelf'.
```
Run the following command:
```bash
sudo vagrant plugin install vagrant-berkshelf
vagrant up
```
