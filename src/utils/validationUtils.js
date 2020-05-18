const REGEXES = {
  TEXT: /^[a-zA-Z\s]+$/,
  EMAIL: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
};

/** validates a text input which expects a non empty data.
 * @param  {string} input
 * @return {string} An error message when fails validity. else, returns '' for valid
 */
export function validateNonEmpty(input) {
  if (!input) {
    return false;
  }
  return true;
}

/**
 * validates a text input which expects an email.
 * @param  {string} input
 * @return {string} An error message when fails validity. else, returns '' for valid
 */
export function validateEmail(input) {
  let sErrorMsg = '';
  if (!input) {
    sErrorMsg = 'This field cannot be blank';
  } else if (!REGEXES.EMAIL.test(input)) {
    sErrorMsg = 'Please provide a valid email address.';
  }
  return sErrorMsg;
}

/**
 * validates a text input which expects only letters.
 * @param  {string} input
 * @return {string} An error message when fails validity. else, returns '' for valid
 */
export function validateTextInput(input) {
  let sErrorMsg = '';
  if (!input) {
    sErrorMsg = 'This field cannot be blank';
  } else if (!REGEXES.TEXT.test(input)) {
    sErrorMsg = 'Only letters are allowed for this field';
  }
  return sErrorMsg;
}
