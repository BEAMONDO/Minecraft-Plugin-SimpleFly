package davigamer161.simplefly.comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import davigamer161.simplefly.SimpleFly;

public class ComandoFly implements CommandExecutor{

	private SimpleFly plugin;
	private ArrayList<Player> list_of_flying_players = new ArrayList();

	public ComandoFly(SimpleFly plugin){
        this.plugin = plugin;
    }
	
	@Override
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args){
		if(sender instanceof Player){
			FileConfiguration config = plugin.getConfig();
			Player jugador = (Player) sender;
			if(args.length == 0){
				flyMethod(jugador);
			}else if(args.length == 1){
				if(jugador.hasPermission("simplefly.fly.others")){
					Player target = Bukkit.getPlayer(args[0]);
                	flyMethod(target);
				}else{
					String path = "Config.no-perm";
					if(config.getString(path).equals("true")){
					List<String> mensaje = config.getStringList("Config.no-perm-text");
						for(int i=0;i<mensaje.size();i++){
							String texto = mensaje.get(i);
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
						}
					}
				}
			}
		}
		return false;
	}
	private void flyMethod(Player jugador){
		FileConfiguration config = plugin.getConfig();
		if(jugador.hasPermission("simplefly.fly")){
				if(list_of_flying_players.contains(jugador)){
					list_of_flying_players.remove(jugador);
					jugador.setAllowFlight(false);
					String path = "Config.fly-message";
					if(config.getString(path).equals("true")){
					List<String> mensaje = config.getStringList("Config.fly-message-disabled-text");
						for(int i=0;i<mensaje.size();i++){
							String texto = mensaje.get(i);
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
						}
					}
				}else if(!(list_of_flying_players.contains(jugador))){
					list_of_flying_players.add(jugador);
					jugador.setAllowFlight(true);
					String path = "Config.fly-message";
					if(config.getString(path).equals("true")){
					List<String> mensaje = config.getStringList("Config.fly-message-enabled-text");
						for(int i=0;i<mensaje.size();i++){
							String texto = mensaje.get(i);
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
						}
					}
				}
			}else{
				String path = "Config.no-perm";
                if(config.getString(path).equals("true")){
                List<String> mensaje = config.getStringList("Config.no-perm-text");
                    for(int i=0;i<mensaje.size();i++){
                        String texto = mensaje.get(i);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                    }
				}
			}
	}
}
