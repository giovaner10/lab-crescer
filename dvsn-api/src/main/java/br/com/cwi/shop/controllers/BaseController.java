package br.com.cwi.shop.controllers;

import br.com.cwi.shop.dtos.BadRequestDto;
import br.com.cwi.shop.dtos.UsuarioLogadoDto;
import br.com.cwi.shop.helpers.Constantes;
import br.com.cwi.shop.helpers.CookieHelper;
import br.com.cwi.shop.helpers.StringHelper;
import br.com.cwi.shop.repository.UsuarioRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    @Autowired
    UsuarioRepository usuarioRepository;

    protected UsuarioLogadoDto obterUsuarioLogado(HttpServletRequest request){
        var cookie = CookieHelper.getCookieValue(request, Constantes.AUTH_COOKIE_NAME);
        if(cookie != null) {
            try {
                var userJson = StringHelper.fromBase64(cookie);
                var usuarioLogado = StringHelper.fromJson(userJson, UsuarioLogadoDto.class);
                var usuarioDb = usuarioRepository.buscarPorId(usuarioLogado.getId());

                if(usuarioDb == null)
                    return null;

                return new UsuarioLogadoDto(usuarioDb);
            } catch (JsonProcessingException ex) {
                System.err.println(ex);
            }
        }
        return null;
    }

    protected ResponseEntity<BadRequestDto> badRequest(String erro) {
        return new ResponseEntity(new BadRequestDto(erro), HttpStatus.BAD_REQUEST);
    }
}