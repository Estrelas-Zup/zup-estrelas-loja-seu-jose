package br.com.zup.estrelas.lojapecas.service;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.zup.estrelas.lojapecas.dao.VendaDAO;

@RunWith(MockitoJUnitRunner.class)
public class VendaServiceTests {
    
    @Mock
    VendaDAO vendaDao;

    @Mock
    PecaService pecaService;
    

    @InjectMocks
    VendaService vendaService;
    
    
}
