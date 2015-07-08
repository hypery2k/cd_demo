name             'wildfly-clu'
maintainer       'Martin Reinhardt'
maintainer_email 'contact@martinreinhardt-online.de'
license          'GPL'
description      'Installs/Configures wildfly-clu in domain mode'
long_description IO.read(File.join(File.dirname(__FILE__), 'README.md'))
version          '0.2.0'

%w{ wildfly-clu java cron logrotate }.each do |p|
  depends p
end
