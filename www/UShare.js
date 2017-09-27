// Author: Yu Chen <yu.chen@live.ie>
// License: Apache License 2.0

'use strict';

module.exports = {
  /**
   * @param {object|string} options
   * @param {Function} successCallback ['success']
   * @param {Function} errorCallback ['fail'|'cancel'|'invalid']
   */
  share: function (param, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "UShare", "share", [JSON.stringify(param)]);
  }
};