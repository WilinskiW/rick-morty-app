import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';
import { provideRouter } from '@angular/router';

describe("HomeComponent (integration test)", () => {
  let fixture: ComponentFixture<HomeComponent>;
  let component: HomeComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideRouter([])],
      imports: [HomeComponent]
    })
    fixture = TestBed.createComponent(HomeComponent);
    component = fixture.componentInstance;
  })

  it("should create Home component", () => {
    expect(component).toBeTruthy();
  });

  it("should render three section tabs", () => {
    const sectionTabs = fixture.nativeElement.querySelectorAll("app-section-tab");
    expect(sectionTabs.length).toEqual(3);
  })

  it("should have a link to github account", async () => {
    await fixture.whenStable();
    fixture.detectChanges();

    const repoLink: HTMLAnchorElement = fixture.nativeElement.querySelector("a.my-info-link");
    expect(repoLink).toBeTruthy();
    expect(repoLink.href).toEqual("https://github.com/WilinskiW")
    expect(repoLink.textContent).toEqual("Created by: Wiktor Wiliński")
  });
});
