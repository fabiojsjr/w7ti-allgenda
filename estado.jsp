<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%try{ %>
									<option value="AC" <% if( returnNotNull(entity.getEstado()).equals("AC") ) out.print("selected"); %>>Acre</option>
									<option value="AL" <% if( returnNotNull(entity.getEstado()).equals("AL") ) out.print("selected"); %>>Alagoas</option>
									<option value="AP" <% if( returnNotNull(entity.getEstado()).equals("AP") ) out.print("selected"); %>>Amapá</option>
									<option value="AM" <% if( returnNotNull(entity.getEstado()).equals("AM") ) out.print("selected"); %>>Amazonas</option>
									<option value="BA" <% if( returnNotNull(entity.getEstado()).equals("BA") ) out.print("selected"); %>>Bahia</option>
									<option value="CE" <% if( returnNotNull(entity.getEstado()).equals("CE") ) out.print("selected"); %>>Ceará</option>
									<option value="DF" <% if( returnNotNull(entity.getEstado()).equals("DF") ) out.print("selected"); %>>Distrito Federal</option>
									<option value="ES" <% if( returnNotNull(entity.getEstado()).equals("ES") ) out.print("selected"); %>>Espirito Santo</option>
									<option value="GO" <% if( returnNotNull(entity.getEstado()).equals("GO") ) out.print("selected"); %>>Goiás</option>
									<option value="MA" <% if( returnNotNull(entity.getEstado()).equals("MA") ) out.print("selected"); %>>Maranhão</option>
									<option value="MT" <% if( returnNotNull(entity.getEstado()).equals("MT") ) out.print("selected"); %>>Mato Grosso</option>
									<option value="MS" <% if( returnNotNull(entity.getEstado()).equals("MS") ) out.print("selected"); %>>Mato Grosso do Sul</option>
									<option value="MG" <% if( returnNotNull(entity.getEstado()).equals("MG") ) out.print("selected"); %>>Minas Gerais</option>
									<option value="PA" <% if( returnNotNull(entity.getEstado()).equals("PA") ) out.print("selected"); %>>Pará</option>
									<option value="PB" <% if( returnNotNull(entity.getEstado()).equals("PB") ) out.print("selected"); %>>Paraiba</option>
									<option value="PR" <% if( returnNotNull(entity.getEstado()).equals("PR") ) out.print("selected"); %>>Paraná</option>
									<option value="PE" <% if( returnNotNull(entity.getEstado()).equals("PE") ) out.print("selected"); %>>Pernambuco</option>
									<option value="PI" <% if( returnNotNull(entity.getEstado()).equals("PI") ) out.print("selected"); %>>Piauí</option>
									<option value="RJ" <% if( returnNotNull(entity.getEstado()).equals("RJ") ) out.print("selected"); %>>Rio de Janeiro</option>
									<option value="RN" <% if( returnNotNull(entity.getEstado()).equals("RN") ) out.print("selected"); %>>Rio Grande do Norte</option>
									<option value="RS" <% if( returnNotNull(entity.getEstado()).equals("RS") ) out.print("selected"); %>>Rio Grande do Sul</option>
									<option value="RO" <% if( returnNotNull(entity.getEstado()).equals("RO") ) out.print("selected"); %>>Rondônia</option>
									<option value="RR" <% if( returnNotNull(entity.getEstado()).equals("RR") ) out.print("selected"); %>>Roraima</option>
									<option value="SC" <% if( returnNotNull(entity.getEstado()).equals("SC") ) out.print("selected"); %>>Santa Catarina</option>
									<option value="SP" <% if( returnNotNull(entity.getEstado()).equals("SP") ) out.print("selected"); %>>São Paulo</option>
									<option value="SE" <% if( returnNotNull(entity.getEstado()).equals("SE") ) out.print("selected"); %>>Sergipe</option>
									<option value="TO" <% if( returnNotNull(entity.getEstado()).equals("TO") ) out.print("selected"); %>>Tocantis</option>
<%}catch(NullPointerException ex){ %>
<%} %>								