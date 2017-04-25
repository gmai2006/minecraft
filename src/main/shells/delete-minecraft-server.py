import shutil
import subprocess
import os

        
def exec_function(cmd):
    ps = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    output = ps.communicate()[0]
    return output             
           

def delete_minecraft_server(userid, servername):
    TARGET = "/home/" + userid + "/"
    MCPATH = TARGET + servername + "/";
    
    script = "/etc/init.d/startup-" + servername + ".sh"
    
    #stop minecraft server if it is running
    cmd = "nohup " + script + " stop "
    output = exec_function(cmd)
    
    #remove the startup script
    cmd = "update-rc.d -f " + script + " remove"
    output = exec_function(cmd)
    
    #physically delete the startup script
    cmd = "rm $script "
    output = exec_function(cmd)
    if (os.path.isdir(MCPATH)):
        shutil.rmtree(MCPATH)
    
    print "[[0:Successfully remove minecraft server:" + servername + "]]"
    
def main():
    if len(sys.argv) < 2:
        print "[[99:please input: userId, server name]]"
        sys.exit(0)
    delete_minecraft_server(sys.argv[1], sys.argv[2])
if __name__ == '__main__':
    main()

