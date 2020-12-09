/**
 * Условие:
 * Склонируйте заготовку проекта по ссылке и просмотрите его.
 * Модуль Spring Security позволяет нам внедрять права доступа, а также контролировать их исполнение без ручных проверок.
 * Spring Security базируется на 2х интерфейсах, которые определяют связь сущностей с секьюрностью: UserDetails и GrantedAuthority.
 * UserDetails - то, что будет интерпретироваться системой как пользователь.
 * GrantedAuthority - сущность, описывающая права юзера.
 * Оба эти интерфейса имеют множество реализаций: просмотрите класс SecurityConfig, в методе configure() с помощью настроек inMemoryAuthentication() мы собираем единственный на всю программу экземпляр UserDetails с именем и паролем админ-админ, а его роль “ADMIN” так же будет преобразована в экземпляр GrantedAuthority.
 * Это простейший способ создания секьюрности. Так же мы можем использовать jdbc-аутентификацию путем написания запроса, возвращающего пользователя и роль.
 * Как вы понимаете, такие способы максимально просты, но лишены достаточной гибкости, потому наиболее часто используемый вариант настройки выглядит как имплементация UserDetails и GrantedAuthority в классах-сущностях с переопределением существующих методов.
 *
 * Рассмотрим приложение.
 * Новые классы:
 * - SpringSecurityInitializer - обязателен для не boot-приложения. Кода в нем нет, но требуется для регистрации секьюрити в Спринг-контейнере.
 * - SecurityConfig - настройка секьюрности по определенным URL, а также настройка UserDetails и GrantedAuthority.
 * - LoginSuccessHandler - хэндлер, содержащий в себе алгоритм действий при успешной аутентификации. Например, тут мы можем отправить пользователя с ролью админа на админку после логина, а с ролью юзер на главную страницу сайта и т.п.
 *
 *
 * Задание:
 * 1. Перенесите классы и зависимости из примера в свое MVC приложение из предыдущей задачи.
 * 2. Создайте класс Role и свяжите User с ролями так, чтобы юзер мог иметь несколько ролей.
 * 3. Имплементируйте модели Role и User интерфейсами GrantedAuthority и UserDetails соответственно. Измените настройку
 * секьюрности с inMemory на userDetailService.
 * 4. Все CRUD-операции и страницы для них должны быть доступны только пользователю с ролью admin по url: /admin/.
 * 5. Пользователь с ролью user должен иметь доступ только к своей домашней странице /user, где выводятся его данные.
 * Доступ к этой странице должен быть только у пользователей с ролью userи admin. Не забывайте про несколько ролей у
 * пользователя!
 * 6. Настройте logout с любой страницы с использованием возможностей thymeleaf.
 * 7. Настройте LoginSuccessHandler так, чтобы админа после логина направляло на страницу /admin, а юзера на его страницу.
 */

package ru.itsinfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.itsinfo.model.User;

@Controller
public class IndexController {

	/*
        <sec:authorize access="!isAuthenticated()">
            <p><a class="btn btn-lg btn-success" href="<c:url value="/login" />" role="button">Войти</a></p>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <p>Ваш логин: <sec:authentication property="principal.username" /></p>
            <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>

        </sec:authorize>
	 */

	@GetMapping()
	public String mainPage(Model model) {
		// TODO при ?logout выводить прощальное сообщение
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if (authentication.isAuthenticated()) {
//			System.out.println(authentication.getPrincipal());
//			System.out.println(authentication.getAuthorities());
//		}
		// TODO как отловить неудачный логин "/?error" @PostMapping
		return "index";
	}

	@GetMapping("/accessdenied")
	public String accessDenied(Model model) {
		return "accessdenied";
	}

//	@PostMapping("/j_spring_security_check")
//	public void getLogin(@RequestParam(required = false, defaultValue = "" ) String j_username,
//						 @RequestParam(required = false, defaultValue = "" ) String j_password,
//						 Model model) {
//		System.out.println("lOGIN: " + j_username);
//		System.out.println("PASSWORD: " + j_password);
//	}

//	@GetMapping("logout")
//	public RedirectView logout(RedirectAttributes attributes) {
//		attributes.addAttribute("logout");
//		return new RedirectView("/");
//	}
}