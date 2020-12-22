import {Component, OnDestroy, OnInit} from '@angular/core';
import {RegistrationService} from "../../services/registration.service";
import {AuthService} from "../../services/auth.service";
import {ActivatedRoute, Router} from "@angular/router";
import {LanguageService} from "../../services/language.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit, OnDestroy {

  constructor(public registrationService: RegistrationService,
              public authService: AuthService,
              private route: ActivatedRoute,
              private languageService: LanguageService,
              public router: Router) { }

  ngOnInit() {

    //Note that we deliberately use a snapshot rather than a subscribe
    //so that we pick up the lang query even if there is a redirect.
    //eg like login redirects to /home if already authenticated.
    //With a subscribe, the query can be lost in the redirect.
    const lang = this.route.snapshot.queryParams['lang'];
    //Need to delay changing language otherwise you get ExpressionChangedAfterItHasBeenCheckedError
    setTimeout(
      () => this.languageService.changeLanguage(lang), 1000
    )

    this.registrationService.start();
  }

  ngOnDestroy(): void {
    this.registrationService.stop();
  }

  logout() {
    this.authService.logout().subscribe(() => this.router.navigate(['']));
  }
}

