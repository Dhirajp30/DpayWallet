import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'search'
})
export class SearchPipe implements PipeTransform {
  transform(usersList: any, searchText: any): any {
    let newList: any;

    if (searchText) {
      newList = usersList.filter(user => user.customerName.toLowerCase().startsWith(searchText.toLowerCase()) || user.mobileNumber.toLowerCase().startsWith(searchText.toLowerCase()));
    }
    else {
      newList = usersList;
    }

    return newList;
  }
}
