package davigamer161.simplefly.comandos;

import java.util.ArrayList;

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
				if(jugador.hasPermission("simplefly.fly")){
					if(config.getString(poth).equals("true")){
						Economy econ = plugin.getEconomy();
						double dinero = econ.getBalance(jugador);
						int precio = Integer.valueOf(config.getString("Config.fly-price"));
						if(jugador.hasPermission("simplefly.econ.exempt")){
							if(list_of_flying_players.contains(jugador)){
								disableFlyMethod(jugador);
							}else if(!(list_of_flying_players.contains(jugador))){
								enableFlyMethod(jugador);
							}
						}else if(list_of_flying_players.contains(jugador)){
							disableFlyMethod(jugador);
						}else if(dinero >=precio && (!(list_of_flying_players.contains(jugador)))){
								econ.withdrawPlayer(jugador, precio);
								enableFlyMethod(jugador);
						}else{
							String path = "Config.fly-message";
							if(config.getString(path).equals("true")){
								String mensaje = messages.getString("Messages.fly.no-enought-money");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
							}
						}
					}else{
						if(list_of_flying_players.contains(jugador)){
							disableFlyMethod(jugador);
						}
						else if(!(list_of_flying_players.contains(jugador))){
							enableFlyMethod(jugador);
						}
					}
				}else if(!(jugador.hasPermission("simplefly.fly")) && list_of_flying_players.contains(jugador)){
					disableFlyMethod(jugador);
				}else{
					noPermMethod(jugador);
				}
			}else if(args.length == 1){
				String poth = "Config.pay-to-fly";
				if(jugador.hasPermission("simplefly.fly.others")){
					if(config.getString(poth).equals("true")){
						Economy econ = plugin.getEconomy();
						double dinero = econ.getBalance(jugador);
						int precio = Integer.valueOf(config.getString("Config.fly-price"));
						Player target = Bukkit.getPlayer(args[0]);
						String path = "Config.fly-message";
						if(jugador.hasPermission("simplefly.econ.exempt.others")){
							if(list_of_flying_players.contains(target)){
								disableFlyMethod(target);
								if(config.getString(path).equals("true")){
									String mensaje = messages.getString("Messages.fly.disabled-others");
									jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
								}
							}else if(!(list_of_flying_players.contains(target))){
								enableFlyMethod(target);
								if(config.getString(path).equals("true")){
									String mensaje = messages.getString("Messages.fly.enabled-others");
									jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
								}
							}
						}else if(list_of_flying_players.contains(target)){
							disableFlyMethod(target);
							if(config.getString(path).equals("true")){
								String mensaje = messages.getString("Messages.fly.disabled-others");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}else if(dinero >=precio && (!(list_of_flying_players.contains(target)))){
							econ.withdrawPlayer(jugador, precio);
							enableFlyMethod(target);
							if(config.getString(path).equals("true")){
								String mensaje = messages.getString("Messages.fly.enabled-others");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}else{
							if(config.getString(path).equals("true")){
								String mensaje = messages.getString("Messages.fly.no-enought-money-others");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
					}else{
						Player target = Bukkit.getPlayer(args[0]);
						if(list_of_flying_players.contains(target)){
							disableFlyMethod(target);
							String path = "Config.fly-message";
							if(config.getString(path).equals("true")){
								String mensaje = messages.getString("Messages.fly.disabled-others");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}else if(!(list_of_flying_players.contains(target))){
							enableFlyMethod(target);
							String path = "Config.fly-message";
							if(config.getString(path).equals("true")){
								String mensaje = messages.getString("Messages.fly.enabled-others");
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
					}
				}else{
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
			String mensaje = messages.getString("Messages.fly.disabled");
			jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
		}
	}
	private void enableFlyMethod(Player jugador){
		list_of_flying_players.add(jugador);
		jugador.setAllowFlight(true);
		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		String path = "Config.fly-message";
		if(config.getString(path).equals("true")){
			String mensaje = messages.getString("Messages.fly.enabled");
			jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
		}
	}
	private void noPermMethod(Player jugador){
		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		String path = "Config.no-perm-message";
		if(config.getString(path).equals("true")){
			String mensaje = messages.getString("Messages.no-perm");
            jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
		}
	}
}