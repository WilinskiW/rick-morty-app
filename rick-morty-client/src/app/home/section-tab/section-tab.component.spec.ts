import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SectionTabComponent } from './section-tab.component';
import { provideRouter } from '@angular/router';

describe("SectionTabComponent", () => {
  let fixture: ComponentFixture<SectionTabComponent>;
  let component: SectionTabComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideRouter([])],
      imports: [SectionTabComponent]
    });
    fixture = TestBed.createComponent(SectionTabComponent);
    component = fixture.componentInstance;
  });

  it("should create Section tab component (integration test)", () => {
      expect(fixture).toBeTruthy();
  });

  it("should render title (integration test)", async () => {
    await fixture.whenStable();
    setSectionName("Locations");
    const title = fixture.nativeElement.querySelector("h3").textContent;
    expect(title).toEqual("Locations");
  });

  it("should should create link for section", async () => {
    await fixture.whenStable();
    setSectionName("Characters");
    expect(component.wikiLink).toEqual("/wiki/characters");
  });

  it("should render link to section (integration test)", async () => {
    await fixture.whenStable();
    setSectionName("Episodes");
    const sectionLink = fixture.nativeElement.querySelector("a") as HTMLAnchorElement;
    const link = sectionLink.href;
    expect(link).toContain("/wiki/episodes")
  });

  it("should create correct path for background img (unit test)", async () => {
    await fixture.whenStable();
    setSectionName("Locations");
    expect(component.imageUrl()).toEqual("locations.jpeg");
  });

  it("should render correct image background (integration test)", async () => {
    await fixture.whenStable();
    setSectionName("Characters");
    const sectionCard = fixture.nativeElement.querySelector("div.card") as HTMLAnchorElement;
    expect(sectionCard.style.backgroundImage).toContain("url(\"/images/characters.jpeg\")")
  });

  function setSectionName(sectionName: string){
    fixture.componentRef.setInput("sectionName", sectionName);
    fixture.detectChanges();
  }
});
