package br.com.agenda.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.agenda.model.Contato;
import br.com.agenda.repository.ContatoRepository;
import br.com.agenda.repository.filter.ContatoFilter;
import br.com.agenda.service.ContatoService;
import br.com.agenda.util.jsf.FacesUtil;

@Named("contatoBean")
@ViewScoped
public class ContatoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ContatoRepository contatoRepository;

	@Inject
	private ContatoService contatoService;

	private Contato contato;

	private List<Contato> contatosFiltrados;

	private ContatoFilter contatoFilter;

	public ContatoBean() {
		limpar();
		contatoFilter = new ContatoFilter();
	}

	public boolean isEditando() {
		return this.contato.getId() != null;
	}

	public String novo() {
		return "CadastraContato?faces-redirect=true";
	}

	public String salvar() {
		this.contato = contatoService.salvar(this.contato);
		limpar();
		FacesUtil.addInfoMessage("Contato salvo com sucesso");

		return "PesquisaContato?faces-redirect=true";
	}

	public String excluir() {
		contatoRepository.remover(contato);
		contatosFiltrados.remove(contato);
		FacesUtil.addInfoMessage("Contato " + contato.getNome()
				+ " excluído com sucesso!");
		return "PesquisaContato?faces-redirect=true";
	}

	public void pesquisaContatos() {
		contatosFiltrados = contatoRepository.filtrado(contatoFilter);
	}

	public void limpar() {
		this.contato = new Contato();
	}

	public boolean filterByName(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim();
		if (filterText == null || filterText.equals("")) {
			return true;
		}

		if (value == null) {
			return false;
		}

		String carName = value.toString().toUpperCase();
		filterText = filterText.toUpperCase();

		if (carName.contains(filterText)) {
			return true;
		} else {
			return false;
		}
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public ContatoFilter getContatoFilter() {
		return contatoFilter;
	}

	public void setContatoFilter(ContatoFilter contatoFilter) {
		this.contatoFilter = contatoFilter;
	}

	public List<Contato> getContatosFiltrados() {
		if (this.contatosFiltrados == null) {
			contatosFiltrados = contatoRepository.filtrado(contatoFilter);
		}
		return contatosFiltrados;
	}

}
