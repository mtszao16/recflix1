import React from 'react';

export const FormErrors = ({ formErrors }) => (
  <div style={{ color: 'red', margin: '10px' }}>
    {Object.keys(formErrors).map((fieldName, i) => {
      if (formErrors[fieldName].length > 0) {
        return (
          <p key={i}>
            {fieldName} {formErrors[fieldName]}
          </p>
        );
      } else {
        return '';
      }
    })}
  </div>
);
