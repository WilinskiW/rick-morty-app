import { AbstractControl } from '@angular/forms';

export function cantBeInTheFuture(dateControl: AbstractControl) {
  const controlDate = Date.parse(dateControl.value);
  if (controlDate <= Date.now()) {
    return null;
  }
  return { isInTheFuture: true };
}

export function mustBeCode(codeControl: AbstractControl){
  const regex = new RegExp("S\\d{2}E\\d{2}$");
  if(regex.test(codeControl.value)){
    return null;
  }
  return { isPatternIncorrect: true };
}
