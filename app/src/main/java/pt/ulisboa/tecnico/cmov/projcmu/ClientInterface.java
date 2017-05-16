package pt.ulisboa.tecnico.cmov.projcmu;

import java.util.List;

import pt.ulisboa.tecnico.cmov.projcmu.Shared.Location;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Message;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.Profile;
import pt.ulisboa.tecnico.cmov.projcmu.Shared.User;

public interface ClientInterface {
	
	/*
	 * Funcao usaada para registar um novo utilizador na aplicacao
	 * recebe username e password em plaintext
	 * devolve se a operacao de registo correu com sucesso
	 * ou houve algum utilizador ja tem o mesmo username
	 * */	
	public boolean SignUp(User user); //Done
	
	/*
	 * Funcao usada para iniciar sessao deste utilizador
	 * retorna se a sessao foi iniciada com sucesso
	 * 
	 * Nota: a interface com o cliente deve manter estado da sessao iniciada
	 * */
	public boolean LogIn(User user); //Done
	
	/*
	 * Funcao usada para iniciar sessao deste utilizador
	 * retorna se a sessao foi fechada com sucesso
	 * */
	public boolean LogOut(); //Done
	
	/*
	 * Funcao usada para obter a lista de localizacoes perto do utilizador
	 * Recebe utilizador com localizacao gps do mesmo e ids dos beacons bluetooth encontrados
	 * devolve lista de localizacoes mais proximas. estas localizacoes vem ja com mensagens disponiveis
	 * */
	public List<Location> getLocations(Location loc, List<String> BeaconIds);
	
	/*
	 * Criar uma nova localizacao
	 * */
	public boolean createLocations(Location loc); //Done
	
	/*
	 * Remover uma localizacao criada
	 * */
	public boolean removeLocations(Location loc); //Done

	/*
	 * associar uma mensagem a uma localizacao
	 * */
	public boolean postMessage(Message m); //Done
	
	/*
	 * Remover a mensagem da localizacao
	 * */
	public boolean unpostMessage(Message m); //Done
	
	/*
	 * obtem todas as mensagens disponiveis para qualquer localizacao perto do mesmo
	 * */
	public List<Message> getAvailableMessages(); //Done
	
	/*
	 * Guardar alteracoes ao profile do servidor
	 * */
	public boolean addKeyPair(String key, String value); //Done
	
}
