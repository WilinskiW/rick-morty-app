import {ComponentFixture, TestBed} from '@angular/core/testing';
import {UnauthorizedComponent} from './unauthorized.component';

describe("UnauthorizedComponent", () => {
  let fixture: ComponentFixture<UnauthorizedComponent>;
  let component: UnauthorizedComponent;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [UnauthorizedComponent]
    });
    fixture = TestBed.createComponent(UnauthorizedComponent);
    component = fixture.componentInstance;
  });

  it("should create unauthorize component", () => {
    expect(component).toBeTruthy();
  });

  it("should render Unauthorized (401)", async () => {
    await fixture.whenStable();
    const notFoundText = fixture.nativeElement.querySelector("h1").textContent;

    expect(notFoundText).toEqual("Unauthorized (401)");
  });

  it("should render unauthorizded image", async () => {
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
