import {ComponentFixture, TestBed} from '@angular/core/testing';
import {NotFoundComponent} from './not-found.component';

describe("NotFoundComponent", () => {
  let fixture: ComponentFixture<NotFoundComponent>;
  let component: NotFoundComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [NotFoundComponent]
    });
    fixture = TestBed.createComponent(NotFoundComponent);
    component = fixture.componentInstance;
  });

  it("should create Not found component", () => {
    expect(component).toBeTruthy();
  });

  it("should render Resource not found (404)", async () => {
    await fixture.whenStable();
    const notFoundText = fixture.nativeElement.querySelector("h1").textContent;

    expect(notFoundText).toEqual("Resource not found (404)");
  });

  it("should render resource not found image", async () => {
    await fixture.whenStable();
    const image = fixture.nativeElement.querySelector("img") as HTMLImageElement;
    image.addEventListener("load", () => {
      expect(image.complete).toBeTruthy()
    });
  });

  it("should render button back to home", async () => {
    await fixture.whenStable();
    const backHomeBtn = fixture.nativeElement.querySelector("a.btn") as HTMLAnchorElement;

    expect(backHomeBtn.textContent).toEqual("Back to home");
    expect(backHomeBtn.href).toEqual(window.location.origin + "/");
  });
})
