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
import net.milkbowl.vault.economy.Economy;

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
			FileConfiguration messages = plugin.getMessages();
			Player jugador = (Player) sender;
			if(args.length == 0){
				String poth = "Config.pay-to-fly";
		if(jugador.hasPermission("simplefly.fly") && config.getString(poth).equals("true")){
					Economy econ = plugin.getEconomy();
        			double dinero = econ.getBalance(jugador);
       				int precio = Integer.valueOf(config.getString("Config.fly-price"));
					if(jugador.hasPermission("simplefly.econ.exempt")){
					if(list_of_flying_players.contains(jugador)){
					disableFlyMethod(jugador);
					}
					else if(!(list_of_flying_players.contains(jugador))){
						enableFlyMethod(jugador);
					}
					}else if(dinero >=precio){
					econ.withdrawPlayer(jugador, precio);
				if(list_of_flying_players.contains(jugador)){
					disableFlyMethod(jugador);
				}
				else if(!(list_of_flying_players.contains(jugador))){
					enableFlyMethod(jugador);
				}
			}else{
				String path = "Config.fly-message";
						if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.fly.no-enought-money");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
							}
						}
			}
			}else if(jugador.hasPermission("simplefly.fly")){
				if(list_of_flying_players.contains(jugador)){
					disableFlyMethod(jugador);
				}
				else if(!(list_of_flying_players.contains(jugador))){
					enableFlyMethod(jugador);
				}
			}else{
				noPermMethod(jugador);
			}
			}else if(args.length == 1){
				String poth = "Config.pay-to-fly";
				if(jugador.hasPermission("simplefly.fly.others") && config.getString(poth).equals("true")){
					Economy econ = plugin.getEconomy();
        			double dinero = econ.getBalance(jugador);
       				int precio = Integer.valueOf(config.getString("Config.fly-price"));
					if(jugador.hasPermission("simplefly.econ.exempt.others")){
						Player target = Bukkit.getPlayer(args[0]);
						if(list_of_flying_players.contains(target)){
							disableFlyMethod(target);
							String path = "Config.fly-message";
						if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.fly.disabled-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
						}else if(!(list_of_flying_players.contains(target))){
							enableFlyMethod(target);
							String path = "Config.fly-message";
							if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.fly.enabled-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
							}
						}
					}else if(dinero >=precio){
						Player target = Bukkit.getPlayer(args[0]);
						econ.withdrawPlayer(jugador, precio);
						if(list_of_flying_players.contains(target)){
							disableFlyMethod(target);
							String path = "Config.fly-message";
						if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.fly.disabled-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
						}else if(!(list_of_flying_players.contains(target))){
							enableFlyMethod(target);
							String path = "Config.fly-message";
							if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.fly.enabled-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
							}
						}
				}else{
					Player target = Bukkit.getPlayer(args[0]);
					String path = "Config.fly-message";
							if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.fly.no-enought-money-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
				}
				}else if(jugador.hasPermission("simplefly.fly.others")){
						Player target = Bukkit.getPlayer(args[0]);
						if(list_of_flying_players.contains(target)){
							disableFlyMethod(target);
							String path = "Config.fly-message";
						if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.fly.disabled-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
						}else if(!(list_of_flying_players.contains(target))){
							enableFlyMethod(target);
							String path = "Config.fly-message";
							if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.fly.enabled-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
					}
				}
				else{
					noPermMethod(jugador);
				}
			}
		}
		return false;
	}
	private void disableFlyMethod(Player jugador){
					list_of_flying_players.remove(jugador);
					jugador.setAllowFlight(false);
					FileConfiguration config = plugin.getConfig();
					FileConfiguration messages = plugin.getMessages();
					String path = "Config.fly-message";
					if(config.getString(path).equals("true")){
					List<String> mensaje = messages.getStringList("Messages.fly.disabled");
						for(int i=0;i<mensaje.size();i++){
							String texto = mensaje.get(i);
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
						}
					}
	}
	private void enableFlyMethod(Player jugador){
	list_of_flying_players.add(jugador);
					jugador.setAllowFlight(true);
					FileConfiguration config = plugin.getConfig();
					FileConfiguration messages = plugin.getMessages();
					String path = "Config.fly-message";
					if(config.getString(path).equals("true")){
					List<String> mensaje = messages.getStringList("Messages.fly.enabled");
						for(int i=0;i<mensaje.size();i++){
							String texto = mensaje.get(i);
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
						}
					}
	}
	private void noPermMethod(Player jugador){
		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		String path = "Config.no-perm-message";
		if(config.getString(path).equals("true")){
			List<String> mensaje = messages.getStringList("Messages.no-perm");
			for(int i=0;i<mensaje.size();i++){
				String texto = mensaje.get(i);
				jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
			}
		}
	}
}




