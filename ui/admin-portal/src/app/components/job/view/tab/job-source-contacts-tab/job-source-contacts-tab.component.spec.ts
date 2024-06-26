import {ComponentFixture, fakeAsync, TestBed, tick} from '@angular/core/testing';
import { Job } from '../../../../../model/job';
import { By } from '@angular/platform-browser';
import { JobSourceContactsTabComponent } from './job-source-contacts-tab.component';
import {MockJob} from "../../../../../MockData/MockJob";
import {
  JobSourceContactsWithChatsComponent
} from "../../source-contacts/job-source-contacts-with-chats/job-source-contacts-with-chats.component";
import {HttpClientModule} from "@angular/common/http";
import {HttpClientTestingModule} from "@angular/common/http/testing";
import {LocalStorageModule} from "angular-2-local-storage";
import {
  ViewJobSourceContactsComponent
} from "../../source-contacts/view-job-source-contacts/view-job-source-contacts.component";

fdescribe('JobSourceContactsTabComponent', () => {
  let component: JobSourceContactsTabComponent;
  let fixture: ComponentFixture<JobSourceContactsTabComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports:[HttpClientTestingModule,LocalStorageModule.forRoot({}),
      ],
      declarations: [JobSourceContactsTabComponent,JobSourceContactsWithChatsComponent,ViewJobSourceContactsComponent],
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobSourceContactsTabComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render job source contacts with chats', () => {
    component.job = MockJob;
    component.editable = true;

    fixture.detectChanges();

    const viewJobSourceContactsComponent = fixture.debugElement.query(By.directive(ViewJobSourceContactsComponent)).componentInstance;
    expect(viewJobSourceContactsComponent).toBeTruthy();

    // Now you can access the job property of the app-view-job-source-contacts component
    const jobFromComponent = viewJobSourceContactsComponent.job;
    expect(jobFromComponent).toEqual(component.job);
  });

});
