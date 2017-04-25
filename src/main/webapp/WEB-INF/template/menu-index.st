
    <div class="list-group">
        <a href="$context$create-server.xhtml" class="list-group-item active">
            <span class="fa fa-user"></span> Create new Minecraft server
        </a>
    </div>
      
        $servers:{n|
        <div class="list-group">
        <a href="$context$manage-server.xhtml?id=$n.id$" class="list-group-item">
            <span class="fa fa-server"></span> Manage $n.id$
        </a>
        
         </div>
         }$
    
