import shutil
import subprocess
import sys
import os
import logging
        
def exec_function(cmd):
    ps = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    output = ps.communicate()[0]
    return output             
           
def inplace_change(filename, old_string, new_string):
    s = open(filename).read()
    if old_string in s:
            print 'Changing "{old_string}" to "{new_string}"'.format(**locals())
            s = s.replace(old_string, new_string)
            f = open(filename, 'w')
            f.write(s)
            f.flush()
            f.close()
    else:
            print 'No occurances of "{old_string}" found.'.format(**locals())
           
def create_minecraft_server(userid, servername, memory):
    
    TEMPLATE = "/home/minecraft/template/"
    MINECRAFTFILE = "/home/minecraft/craftbukkit.jar"
    STARTUP = TEMPLATE + "mincraftstartupscript.sh"
    TARGET = "/home/" + userid + "/"
    MCPATH = TARGET + servername + "/";
    
    if (False == os.path.isfile(MINECRAFTFILE)):
        print "[[1:" + MINECRAFTFILE + " not found.]]"
        sys.exit(0)
        
    if (False == os.path.isfile(STARTUP)):
        print "[[2:" + STARTUP + " not found.]]"
        sys.exit(0)
        
    if (False == os.path.isdir(TARGET)):
        print "[[2:" + TARGET + " not found.]]"
        sys.exit(0)
    
    if not os.path.exists(MCPATH):
        os.makedirs(MCPATH)
        
    src_files = os.listdir(TEMPLATE)
    for file_name in src_files:
        
        full_file_name = os.path.join(TEMPLATE, file_name)
        if (os.path.isfile(full_file_name)):
            dest = os.path.join(MCPATH, file_name)
            print file_name + " " + dest
            shutil.copy(full_file_name, dest)
        
    startupscript = MCPATH + "startup-" + servername + ".sh"
    shutil.copyfile(STARTUP, startupscript)   
    
    #replace tokens in the conf file
    inplace_change(startupscript, "@@SERVICE@@", MINECRAFTFILE)
    inplace_change(startupscript, "@@USERID@@", userid)
    inplace_change(startupscript, "@@MCPATH@@", MCPATH)
    inplace_change(startupscript, "@@SESSIONID@@", servername)
    inplace_change(startupscript, "@@MEMORY@@", memory)
    
    exec_function("chown -R " + userid + " " + MCPATH)
    exec_function("chmod 755 " + startupscript)
    
    #change mod startup script
    #os.chmod(startupscriptfile, stat.S_IWUSR | stat.S_IRUSR | stat.S_IXUSR)
#     script = "/etc/init.d/startup-" + servername + ".sh"
#     shutil.copyfile(startupscript, script)   
#     exec_function("chmod 700 " + script)
#     cmd = "update-rc.d " + startupscript + " defaults"
#     output = exec_function(cmd)
     
    cmd = "nohup " + startupscript + " start "
    output = exec_function(cmd)
    print output
    print "[[0:Successfully created minecraft server:" + servername + "]]"

def main():
    if len(sys.argv) < 3:
        print "[[99:please input: userId, server name, & memory size]]"
        sys.exit(0)
    logging.basicConfig(filename='.log', level=logging.INFO)
    create_minecraft_server(sys.argv[1], sys.argv[2], sys.argv[3])
if __name__ == '__main__':
    main()

