export interface PageModel<T> {
  content: T[],
  first: boolean,
  last: boolean,
  number: number,
  totalPages: number,
  totalElements: number
}
