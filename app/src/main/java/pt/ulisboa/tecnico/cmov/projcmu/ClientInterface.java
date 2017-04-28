package pt.ulisboa.tecnico.cmov.projcmu;

import java.util.List;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Message;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Profile;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;

public interface ClientInterface {
	
	/*
	 * Função usaada para registar um novo utilizador na aplicação
	 * recebe username e password em plaintext
	 * devolve se a operação de registo correu com sucesso 
	 * ou houve algum utilizador já tem o mesmo username
	 * */	
	public boolean SignUp(User user); //Done
	
	/*
	 * Função usada para iniciar sessão deste utilizador
	 * retorna se a sessão foi iniciada com sucesso
	 * 
	 * Nota: a interface com o cliente deve manter estado da sessão iniciada
	 * */
	public boolean LogIn(User user); //Done
	
	/*
	 * Função usada para iniciar sessão deste utilizador
	 * retorna se a sessão foi fechada com sucesso
	 * */
	public boolean LogOut(); //Done
	
	/*
	 * Funcão usada para obter a lista de localizações perto do utilizador
	 * Recebe utilizador com localização gps do mesmo e ids dos beacons bluetooth encontrados
	 * devolve lista de localizações mais próximas. estas localizações vêm já com mensagens disponíveis
	 * */
	public List<Location> getLocations(Location loc, List<Integer> BeaconIds);
	
	/*
	 * Criar uma nova localização
	 * */
	public boolean createLocations(Location loc); //Done
	
	/*
	 * Remover uma localização criada
	 * */
	public boolean removeLocations(Location loc); //Done

	/*
	 * associar uma mensagem a uma localização
	 * */
	public boolean postMessage(Message m); //Done
	
	/*
	 * Remover a mensagem da localização
	 * */
	public boolean unpostMessage(Message m); //Done
	
	/*
	 * obtem todas as mensagens disponíveis para qualquer localização perto do mesmo
	 * */
	public List<Message> getAvailableMessages(); //Done
	
	/*
	 * Guardar alterações ao profile do servidor
	 * */
	public boolean addKeyPair(String key, String value); //Done
	
}
